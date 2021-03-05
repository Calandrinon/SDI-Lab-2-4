package Main.Model;

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

    public static String fileWriter(Record record){
        return record.getId() + " | " + record.Price + " | " + record.AlbumName + " | " + record.InStock + " | " + record.TypeOfRecord;
    }

    public static Record fileReader(String line){
        return new Record(1, "a", 1, RecordType.CD);
    }
}
