package Repository;

import Exceptions.ValidationException;
import Model.BaseEntity;
import Validator.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;


public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    private final Map<ID, T> entities;
    private final Validator<T> validator;

    /**
     * @param validator - validator for the entities
     */
    public InMemoryRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<T> findOne(ID id) {
        Optional.ofNullable(id).orElseThrow(() -> new IllegalArgumentException("id must not be null"));
        return Optional.ofNullable(entities.get(id));
    }

    /**
     *
     * @return all entities.
     */
    @Override
    public Iterable<T> findAll() {
        return new HashSet<>(entities.values());
    }

    /**
     * Saves the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidationException
     *             if the entity is not valid.
     */
    @Override
    public Optional<T> save(T entity) throws ValidationException {
        Optional.ofNullable(entity).orElseThrow(() -> new ValidationException("entity must not be null"));
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    /**
     * Removes the entity with the given id.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<T> delete(ID id) {
        Optional.ofNullable(id).orElseThrow(() -> new IllegalArgumentException("id must not be null"));
        return Optional.ofNullable(entities.remove(id));
    }

    /**
     * Updates the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id does not exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidationException
     *             if the entity is not valid.
     */
    @Override
    public Optional<T> update(T entity) throws ValidationException {
        Optional.ofNullable(entity).orElseThrow(() -> new ValidationException("the entity cannot be null"));
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
