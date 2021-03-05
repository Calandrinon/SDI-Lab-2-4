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
    public void setUp() {
        user = new User(FIRST_NAME, LAST_NAME, NUMBER_OF_TRANSACTIONS);
        user.setId(USER_ID);
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void testGetId() {
        assert(USER_ID == user.getId());
    }

    @Test
    public void testSetId() {
        user.setId(NEW_USER_ID);
        assert(NEW_USER_ID == user.getId());
    }

    @Test
    public void testGetFirstName() {
        assert(FIRST_NAME.equals(user.getFirstName()));
    }

    @Test
    public void testGetLastName() {
        assert(LAST_NAME.equals(user.getLastName()));
    }

    @Test
    public void testGetNumberOfTransactions() {
        assert(NUMBER_OF_TRANSACTIONS == user.getNumberOfTransactions());
    }

}
