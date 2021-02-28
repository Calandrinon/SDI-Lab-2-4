package Test.Model;

import org.junit.After;
import org.junit.Before;
import Main.Model.User;
import org.junit.Test;

public class TestUser {
    private static final int USER_ID = 1;
    private static final int NEW_USER_ID = 2;
    private static final String FIRST_NAME = "A";
    private static final String LAST_NAME = "A";
    private static final int NUMBER_OF_TRANSACTIONS = 1;

    private User user;

    @Before
    public void setUp() throws Exception
    {
        user = new User(FIRST_NAME, LAST_NAME, NUMBER_OF_TRANSACTIONS);
        user.setId(USER_ID);
    }

    @After
    public void tearDown() throws Exception {
        user = null;
    }

    @Test
    public void testGetId() throws Exception{
        assert(USER_ID == user.getId());
    }

    @Test
    public void testSetId() throws Exception{
        user.setId(NEW_USER_ID);
        assert(NEW_USER_ID == user.getId());
    }

    @Test
    public void testGetFirstName() throws Exception{
        assert(FIRST_NAME.equals(user.getFirstName()));
    }

    @Test
    public void testGetLastName() throws Exception{
        assert(LAST_NAME.equals(user.getLastName()));
    }

    @Test
    public void testGetNumberOfTransactions() throws Exception{
        assert(NUMBER_OF_TRANSACTIONS == user.getNumberOfTransactions());
    }

}
