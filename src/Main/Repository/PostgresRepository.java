package Repository;

import Exceptions.ValidationException;
import Model.*;
import Model.Record;

import java.sql.ClientInfoStatus;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PostgresRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    //private Validator<T> validator;
    private String tableName, url, username, password;

    public PostgresRepository(/*Validator<T> validator,*/ String tableName, String url) {
        //this.validator = validator;
        this.tableName = tableName;
        this.url = url;
        this.username = "calandrinon";
        this.password = "12345";
    }

    @Override
    public Optional<T> findOne(ID id) {
        return Optional.empty();
    }

    /**

    @Override
    public Optional<T> findOne(ID id) {
        String query = "SELECT * FROM " + this.tableName + " AS " + this.tableName + "(IdColumn) WHERE IdColumn = ?";

        try (var connection = DriverManager.getConnection(url, username, password);
             var parameterSetter = connection.prepareStatement(query)) {
            parameterSetter.setInt(1, (Integer) id);

            var queryExecutor = parameterSetter.executeQuery();
            if (queryExecutor.next()) {
                int actualId = queryExecutor.getInt(1);
                String firstName = queryExecutor.getString(2);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return Optional.empty();
    }
     **/

    private String getTypesOfTheTableFromTheDatabase(Connection connection) throws SQLException {
        var columnDataTypesQuery = connection.prepareStatement("SELECT data_type FROM information_schema.columns WHERE table_name=?");
        columnDataTypesQuery.setString(1, this.tableName.toLowerCase());
        var columnDataTypes = columnDataTypesQuery.executeQuery();
        String types = "";
        while (columnDataTypes.next()) {
            types = types.concat(columnDataTypes.getString(1)).concat(",");
        }

        types = types.substring(0, types.length() - 1);
        return types;
    }


    public String getColumnsOfTheTableFromTheDatabase(Connection connection) throws SQLException {
        var columnDataTypesQuery = connection.prepareStatement("SELECT column_name FROM information_schema.columns WHERE table_name=?");
        columnDataTypesQuery.setString(1, this.tableName.toLowerCase());
        var columnDataTypes = columnDataTypesQuery.executeQuery();
        String types = "";
        while (columnDataTypes.next()) {
            types = types.concat(columnDataTypes.getString(1)).concat(",");
        }

        types = types.substring(0, types.length() - 1);
        return types;
    }


    private T getEntityFromDatabase(Connection connection, String attributes) throws SQLException, ParseException {
        String types = this.getTypesOfTheTableFromTheDatabase(connection);
        String[] listOfAttributes = attributes.split(",");
        if (types.equals("integer,character varying,real,integer,character varying")) {
            int id = Integer.parseInt(listOfAttributes[0]);
            String albumName = listOfAttributes[1];
            int price = Integer.parseInt(listOfAttributes[2]);
            int inStock = Integer.parseInt(listOfAttributes[3]);
            RecordType typeOfRecord = null;
            switch (listOfAttributes[4]) {
                case "CD" -> typeOfRecord = RecordType.CD;
                case "VINYL" -> typeOfRecord = RecordType.VINYL;
                case "TAPE" -> typeOfRecord = RecordType.TAPE;
            }

            Record record = new Record(price, albumName, inStock, typeOfRecord);
            record.setId(id);
            return (T) record;
        } else if (types.equals("integer,character varying,character varying,integer")) {
            int id = Integer.parseInt(listOfAttributes[0]);
            String firstName = listOfAttributes[1];
            String lastName = listOfAttributes[2];
            int numberOfTransactions = Integer.parseInt(listOfAttributes[3]);

            User user = new User(firstName, lastName, numberOfTransactions);
            user.setId(id);

            return (T) user;
        } else if (types.equals("integer,integer,integer,date,integer")) {
            int transactionId = Integer.parseInt(listOfAttributes[0]);
            int userId = Integer.parseInt(listOfAttributes[1]);
            int recordId = Integer.parseInt(listOfAttributes[2]);

            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(listOfAttributes[3]);
            int quantity = Integer.parseInt(listOfAttributes[4]);

            Transaction transaction = new Transaction(userId, recordId, date, quantity);
            transaction.setId(transactionId);

            return (T) transaction;
        }

        return null;
    }


    @Override
    public Iterable<T> findAll() {
        String query = "SELECT * FROM " + this.tableName;
        List<T> entities = new ArrayList<>();

        try (var connection = DriverManager.getConnection(url, username, password);
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery()) {

            var queryNumberOfColumns = connection.prepareStatement("select count(*) from information_schema.columns where table_name=?");
            queryNumberOfColumns.setString(1, this.tableName.toLowerCase());

            var numberOfColumnsResult = queryNumberOfColumns.executeQuery();
            int numberOfColumns = 0;

            if (numberOfColumnsResult.next()) {
                numberOfColumns = numberOfColumnsResult.getInt(1);
            }

            while (resultSet.next()) {
                String attributes = "";

                for (int i = 1; i <= numberOfColumns; i++) {
                    attributes = attributes.concat(resultSet.getString(i));
                    if (i != numberOfColumns) {
                        attributes = attributes.concat(",");
                    }
                }

                entities.add(this.getEntityFromDatabase(connection, attributes));
            }

        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return entities;
    }

    @Override
    public Optional<T> save(T entity) throws ValidationException {
        String columnNames = "";
        try (var connection = DriverManager.getConnection(url, username, password)) {
            columnNames = this.getColumnsOfTheTableFromTheDatabase(DriverManager.getConnection(url, username, password));

            var queryNumberOfColumns = connection.prepareStatement("select count(*) from information_schema.columns where table_name=?");
            queryNumberOfColumns.setString(1, this.tableName.toLowerCase());
            var numberOfColumnsResult = queryNumberOfColumns.executeQuery();
            int numberOfColumns = 0;

            if (numberOfColumnsResult.next()) {
                numberOfColumns = numberOfColumnsResult.getInt(1);
            }

            String stringWithParameters = "";
            for (int i = 1; i <= numberOfColumns; i++) {
                if (i > 1)
                    stringWithParameters = stringWithParameters.concat(",");
                stringWithParameters = stringWithParameters.concat("?");
            }

            System.out.println("Parameters: " + stringWithParameters);
            System.out.println("Columns: " + columnNames);
            String insertStatement = "insert into " + this.tableName + " (" + columnNames + ") values (" + stringWithParameters + ")";

            try (var preparedStatement = connection.prepareStatement(insertStatement)) {
                if (entity instanceof User) {
                    preparedStatement.setInt(1, ((User)entity).getId());
                    preparedStatement.setString(2, ((User)entity).getFirstName());
                    preparedStatement.setString(3, ((User)entity).getLastName());
                    preparedStatement.setInt(4, ((User)entity).getNumberOfTransactions());
                } else if (entity instanceof Record) {
                    preparedStatement.setInt(1, ((Record)entity).getId());
                    preparedStatement.setString(2, ((Record)entity).getAlbumName());
                    preparedStatement.setInt(3, ((Record)entity).getPrice());
                    preparedStatement.setInt(4, ((Record)entity).getInStock());
                    preparedStatement.setString(5, ((Record)entity).getTypeOfRecord().toString());
                } else if (entity instanceof Transaction) {
                    preparedStatement.setInt(1, ((Transaction)entity).getId());
                    preparedStatement.setInt(2, ((Transaction)entity).getUserID());
                    preparedStatement.setInt(3, ((Transaction)entity).getRecordID());
                    Date date = ((Transaction)entity).getDate();
                    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                    preparedStatement.setDate(4, sqlDate);
                    preparedStatement.setInt(5, ((Transaction)entity).getQuantity());
                }

                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.ofNullable(entity);
    }

    @Override
    public Optional<T> delete(ID id) {
        return Optional.empty();
    }

    @Override
    public Optional<T> update(T entity) throws ValidationException {
        return Optional.empty();
    }

    public int getNumberOfEntities() {
        return (int)this.findAll().spliterator().getExactSizeIfKnown();
    }
}
