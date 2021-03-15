package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.print.Doc;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Record extends BaseEntity<Integer>{
    private final int Price;
    private final String AlbumName;
    private final int InStock;
    private final RecordType TypeOfRecord;

    /**
     * @param price - integer
     * @param AlbumName - string
     * @param inStock - integer
     * @param typeOfRecord - RecordType
     */
    public Record(int price, String AlbumName, int inStock, RecordType typeOfRecord) {
        Price = price;
        this.AlbumName = AlbumName;
        InStock = inStock;
        TypeOfRecord = typeOfRecord;
    }

    /**
     *
     * @return String of the Record object
     */
    @Override
    public String toString() {
        return "Record{" +
                "RecordID=" + getId() +
                ", Price=" + Price +
                ", AlbumName='" + AlbumName + '\'' +
                ", InStock=" + InStock +
                ", TypeOfRecord=" + TypeOfRecord +
                '}';
    }

    /**
     *
     * @param obj - an object to be compared
     * @return true - if the objects are the same or if their ids are the same
     *         false - if the objects don't have the same class
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj)return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Record record = (Record) obj;

        return getId().equals(record.getId());
    }

    /**
     *
     * @return the price of a Record
     */
    public int getPrice() {
        return Price;
    }

    /**
     *
     * @return the album name of a record
     */
    public String getAlbumName() {
        return AlbumName;
    }

    /**
     *
     * @return the amount of stock for a Record
     */
    public int getInStock() {
        return InStock;
    }

    /**
     *
     * @return the record type of a record
     */
    public RecordType getTypeOfRecord() {
        return TypeOfRecord;
    }

    public static Function reader(Function<String, Record> function){
        return function;
    }

    public static Function writer(Function<Record, String> function){
        return function;
    }

    public static String fileWriter(BaseEntity<Integer> baseEntity){
        if (baseEntity.getClass() == Record.class) {
            Record record = (Record) baseEntity;
            return record.getId() + " | " + record.Price + " | " + record.AlbumName + " | " + record.InStock + " | " + record.TypeOfRecord;
        }
        return "";
    }

    public static Record fileReader(String line){
        String[] parts = line.split(" \\| ");
        Record record = new Record(Integer.parseInt(parts[1]), parts[2], Integer.parseInt(parts[3]), RecordType.valueOf(parts[4]));
        record.setId(Integer.parseInt(parts[0]));
        return record;
    }

    public static BiFunction<Record, Document, Node> recordEncoder = (r, d) -> {
        Element recordElement = d.createElement("record");
        recordElement.setAttribute("id", Integer.toString(r.getId()));
        addChildWithTextContent(d, recordElement, "price", Integer.toString(r.getPrice()));
        addChildWithTextContent(d, recordElement, "albumName", r.getAlbumName());
        addChildWithTextContent(d, recordElement, "inStock", Integer.toString(r.getInStock()));
        addChildWithTextContent(d, recordElement, "typeOfRecord", parseRecordTypeToString(r.getTypeOfRecord()));
        return recordElement;
    };

    private static void addChildWithTextContent(Document document, Element parent, String tagName, String textContent){
        Element childElement = document.createElement(tagName);
        childElement.setTextContent(textContent);
        parent.appendChild(childElement);
    }

    public static Function<Element, Record> recordDecoder = e ->{
        Record record = new Record(
                Integer.parseInt(e.getElementsByTagName("price").item(0).getTextContent()),
                e.getElementsByTagName("albumName").item(0).getTextContent(),
                Integer.parseInt(e.getElementsByTagName("inStock").item(0).getTextContent()),
                parseRecordType(e.getElementsByTagName("typeOfRecord").item(0).getTextContent())
        );
        record.setId(Integer.parseInt(e.getAttribute("id")));
        return record;
    };

    private static String parseRecordTypeToString(RecordType recordType){
        switch (recordType){
            case CD -> {
                return "CD";
            }
            case VINYL -> {
                return "VINYL";
            }
            case TAPE -> {
                return "TAPE";
            }
            default -> {
                return "";
            }
        }
    }

    private static RecordType parseRecordType(String string){
        RecordType recordType = null;
        switch (string){
            case "CD" -> recordType = RecordType.CD;
            case "VINYL" -> recordType = RecordType.VINYL;
            case "TAPE" -> recordType = RecordType.TAPE;
        }
        return recordType;
    }
}
