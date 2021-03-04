package Test.Model;

import Main.Model.Record;
import Main.Model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class TestTransaction {
    private final static int TRANSACTION_ID = 1;
    private final static int NEW_TRANSACTION_ID = 2;
    private final static int USER_ID = 1;
    private final static int RECORD_ID = 1;
    private final static Date DATE = new Date();
    private final static int QUANTITY = 1;

    private Transaction transaction;

    @Before
    public void setUp() throws Exception
    {
        transaction = new Transaction(USER_ID, RECORD_ID, DATE, QUANTITY);
        transaction.setId(TRANSACTION_ID);
    }

    @After
    public void tearDown() {
        transaction = null;
    }

    @Test
    public void testGetId() {
        assert(TRANSACTION_ID == transaction.getId());
    }

    @Test
    public void testSetId() {
        transaction.setId(NEW_TRANSACTION_ID);
        assert(NEW_TRANSACTION_ID == transaction.getId());
    }

    @Test
    public void testGeRecordID(){
        assert(RECORD_ID == transaction.getRecordID());
    }

    @Test
    public void testGetUserID(){
        assert(USER_ID == transaction.getUserID());
    }

    @Test
    public void testGetDate(){
        assert(DATE == transaction.getDate());
    }

    @Test
    public void testGetQuantity(){
        assert(QUANTITY == transaction.getQuantity());
    }
}
