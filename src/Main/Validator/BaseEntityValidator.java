package Validator;

import Exceptions.ValidationException;
import Model.BaseEntity;

import java.util.Optional;

public class BaseEntityValidator implements Validator<BaseEntity<Integer>> {
    /**
     *
     * @param entity the given BaseEntity object
     * @throws ValidationException
     *                  if the id of the entity is negative
     */
    @Override
    public void validate(BaseEntity<Integer> entity) throws ValidationException {
        Optional.ofNullable(entity).filter(e -> e.getId() > 0).orElseThrow(() -> new ValidationException("id of entity must be positive"));
    }
}
