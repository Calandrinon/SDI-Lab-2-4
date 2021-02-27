package Model;

public class Record {
    private final int RecordID;
    private final int Price;
    private final String ALbumName;
    private final int InStock;
    private final RecordType TypeOfRecord;

    public Record(int recordID, int price, String ALbumName, int inStock, RecordType typeOfRecord) {
        RecordID = recordID;
        Price = price;
        this.ALbumName = ALbumName;
        InStock = inStock;
        TypeOfRecord = typeOfRecord;
    }

    @Override
    public String toString() {
        return "Record{" +
                "RecordID=" + RecordID +
                ", Price=" + Price +
                ", ALbumName='" + ALbumName + '\'' +
                ", InStock=" + InStock +
                ", TypeOfRecord=" + TypeOfRecord +
                '}';
    }

    public int getRecordID() {
        return RecordID;
    }

    public int getPrice() {
        return Price;
    }

    public String getALbumName() {
        return ALbumName;
    }

    public int getInStock() {
        return InStock;
    }

    public RecordType getTypeOfRecord() {
        return TypeOfRecord;
    }
}
