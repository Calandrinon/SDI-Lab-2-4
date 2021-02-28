package Test.Repository;

import Main.Exceptions.ValidationException;
import Main.Model.BaseEntity;
import Main.Repository.InMemoryRepository;
import Main.Validator.BaseEntityValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;


public class TestInMemoryRepository {
    private static final BaseEntity<Integer> BASE_ENTITY_1 = new BaseEntity<>();
    private static final BaseEntity<Integer> BASE_ENTITY_2 = new BaseEntity<>();
    private static final BaseEntity<Integer> BASE_ENTITY_3 = new BaseEntity<>();
    private static final BaseEntityValidator BASE_ENTITY_VALIDATOR = new BaseEntityValidator();
    private InMemoryRepository<Integer, BaseEntity<Integer>> repository;

    @Before
    public void setUp() {
        BASE_ENTITY_1.setId(1);
        BASE_ENTITY_2.setId(1);
        BASE_ENTITY_3.setId(-1);
        repository = new InMemoryRepository<>(BASE_ENTITY_VALIDATOR);
    }

    @After
    public void tearDown() {
        repository = null;
    }

    @Test
    public void testFindOne(){
        assert(repository.findOne(1).isEmpty());
    }

    @Test
    public void testFindAll() {
        Set<BaseEntity<Integer>> entities = (Set<BaseEntity<Integer>>) repository.findAll();
        assert(entities.isEmpty());
    }

    @Test
    public void testSave() throws ValidationException {
        repository.save(BASE_ENTITY_1);
        assert(repository.findOne(1).get() == BASE_ENTITY_1);
    }

    @Test(expected = ValidationException.class)
    public void testSaveException() throws ValidationException {
        repository.save(BASE_ENTITY_3);
    }

    @Test
    public void testDelete() {
        repository.delete(1);
        assert(repository.findOne(1).isEmpty());
    }

    @Test
    public void testUpdate() throws ValidationException {
        repository.save(BASE_ENTITY_1);
        repository.update(BASE_ENTITY_2);
        assert(repository.findOne(1).get() == BASE_ENTITY_2);
    }

    @Test(expected = ValidationException.class)
    public void testUpdateException() throws ValidationException {
        repository.update(BASE_ENTITY_3);
    }
}
