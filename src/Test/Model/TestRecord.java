package Test.Model;

import Model.Record;
import Model.RecordType;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRecord {
    private static final int USER_ID = 1;
    private static final int NEW_USER_ID = 2;
    private static final int PRICE = 1;
    private static final String ALBUM_NAME = "A";
    private static final int IN_STOCK = 1;
    private static final RecordType TYPE_OF_RECORD = RecordType.CD;

    private Record record;

    @Before
    public void setUp() throws Exception
    {
        record = new Record(PRICE, ALBUM_NAME, IN_STOCK, TYPE_OF_RECORD);
        record.setId(USER_ID);
    }

    @After
    public void tearDown() throws Exception {
        record = null;
    }

    @Test
    public void testGetId() throws Exception{
        assert(USER_ID == record.getId());
    }

    @Test
    public void testSetId() throws Exception{
        record.setId(NEW_USER_ID);
        assert(NEW_USER_ID == record.getId());
    }

    @Test
    public void testGetPrice() throws Exception{
        assert(PRICE == record.getPrice());
    }

    @Test
    public void testGetAlbumName() throws Exception{
        assert(ALBUM_NAME.equals(record.getALbumName()));
    }

    @Test
    public void testGetInStock() throws Exception{
        assert(IN_STOCK == record.getInStock());
    }

    @Test
    public void testGetTypeOfRecord() throws Exception{
        assert(TYPE_OF_RECORD == record.getTypeOfRecord());
    }

}
