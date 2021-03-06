package Model;

import Model.Record;
import Model.RecordType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRecord {
    private static final int RECORD_ID = 1;
    private static final int NEW_RECORD_ID = 2;
    private static final int PRICE = 1;
    private static final String ALBUM_NAME = "A";
    private static final int IN_STOCK = 1;
    private static final RecordType TYPE_OF_RECORD = RecordType.CD;

    private Record record;

    @Before
    public void setUp() {
        record = new Record(PRICE, ALBUM_NAME, IN_STOCK, TYPE_OF_RECORD);
        record.setId(RECORD_ID);
    }

    @After
    public void tearDown() {
        record = null;
    }

    @Test
    public void testGetId() {
        assert(RECORD_ID == record.getId());
    }

    @Test
    public void testSetId() {
        record.setId(NEW_RECORD_ID);
        assert(NEW_RECORD_ID == record.getId());
    }

    @Test
    public void testGetPrice() {
        assert(PRICE == record.getPrice());
    }

    @Test
    public void testGetAlbumName() {
        assert(ALBUM_NAME.equals(record.getAlbumName()));
    }

    @Test
    public void testGetInStock() {
        assert(IN_STOCK == record.getInStock());
    }

    @Test
    public void testGetTypeOfRecord() {
        assert(TYPE_OF_RECORD == record.getTypeOfRecord());
    }

}
