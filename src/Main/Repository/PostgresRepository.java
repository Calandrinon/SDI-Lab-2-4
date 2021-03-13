package Repository;

import Exceptions.ValidationException;
import Model.BaseEntity;
import Validator.Validator;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresRepository {
    //private Validator<T> validator;
    private String tableName, url, username, password;

    public PostgresRepository(/*Validator<T> validator,*/ String tableName, String url) {
        //this.validator = validator;
        this.tableName = tableName;
        this.url = url;
        this.username = "calandrinon";
        this.password = "12345";
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

    public List<String> findAll() {
        String query = "SELECT * FROM " + this.tableName;

        List<String> entitiesAsStrings = new ArrayList<>();

        try (var connection = DriverManager.getConnection(url, username, password);
             var preparedStatement = connection.prepareStatement(query);
             var resultSet = preparedStatement.executeQuery()) {

            var queryNumberOfColumns = connection.prepareStatement("select count(*) from information_schema.columns where table_name='clientuser';");

            var numberOfColumnsResult = queryNumberOfColumns.executeQuery();
            int numberOfColumns = 0;
            if (numberOfColumnsResult.next()) {
                numberOfColumns = numberOfColumnsResult.getInt(1);
                System.out.println("The number of columns has been properly returned!");
            } else {
                System.out.println("The number of columns hasn't been returned.");
            }

            while (resultSet.next()) {
                String value = "";
                for (int i = 1; i <= numberOfColumns; i++) {
                    value = value.concat(resultSet.getString(i));
                    if (i != numberOfColumns) {
                        value = value.concat(",");
                    }
                }

                entitiesAsStrings.add(value);
            }

        } catch (SQLException throwables) {
            System.out.println("FINDALL EXCEPTION MESSAGE: ");
            throwables.printStackTrace();
        }

        return entitiesAsStrings;
    }

    public Optional<String> save(String entity) throws ValidationException {
        return Optional.empty();
    }

    public Optional<String> delete(Integer id) {
        return Optional.empty();
    }

    public Optional<String> update(String entity) throws ValidationException {
        return Optional.empty();
    }
}
