package Model;

public class Record extends BaseEntity<Integer>{
    private final int Price;
    private final String AlbumName;
    private final int InStock;
    private final RecordType TypeOfRecord;

    public Record(int price, String AlbumName, int inStock, RecordType typeOfRecord) {
        Price = price;
        this.AlbumName = AlbumName;
        InStock = inStock;
        TypeOfRecord = typeOfRecord;
    }

    @Override
    public String toString() {
        return "Record{" +
                "RecordID=" + getId() +
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

        return getId() == record.getId();
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
