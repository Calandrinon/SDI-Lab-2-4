package Model;

public class Record extends BaseEntity<Long>{
    private final int RecordID;
    private final int Price;
    private final String AlbumName;
    private final int InStock;
    private final RecordType TypeOfRecord;

    public Record(int recordID, int price, String ALbumName, int inStock, RecordType typeOfRecord) {
        RecordID = recordID;
        Price = price;
        this.AlbumName = ALbumName;
        InStock = inStock;
        TypeOfRecord = typeOfRecord;
    }

    @Override
    public String toString() {
        return "Record{" +
                "RecordID=" + RecordID +
                ", Price=" + Price +
                ", ALbumName='" + AlbumName + '\'' +
                ", InStock=" + InStock +
                ", TypeOfRecord=" + TypeOfRecord +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Record record = (Record) obj;

        return RecordID == record.getRecordID();
    }

    public int getRecordID() {
        return RecordID;
    }

    public int getPrice() {
        return Price;
    }

    public String getALbumName() {
        return AlbumName;
    }

    public int getInStock() {
        return InStock;
    }

    public RecordType getTypeOfRecord() {
        return TypeOfRecord;
    }
}
