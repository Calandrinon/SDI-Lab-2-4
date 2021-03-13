package Repository;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestPostgresRepository {
    private PostgresRepository repository;

    @Before
    public void setUp() {
        this.repository = new PostgresRepository("ClientUser", "jdbc:postgresql://localhost:5432/onlinemusicstore");
    }

    @After
    public void tearDown() {
        this.repository = null;
    }

    @Test
    public void testFindAll_correctNumberOfRows() {
        this.repository = new PostgresRepository("ClientUser", "jdbc:postgresql://localhost:5432/onlinemusicstore");
        List<String> entitiesAsStrings = this.repository.findAll();
        entitiesAsStrings.forEach(System.out::println);
        assert (entitiesAsStrings.size() == 2);
    }
}
