package Main.Validator;

import Main.Exceptions.ValidationException;
import Main.Model.BaseEntity;

public class BaseEntityValidator implements Validator<BaseEntity<Integer>> {
    @Override
    public void validate(BaseEntity<Integer> entity) throws ValidationException {
        if(entity.getId() < 0){
            throw new ValidationException("id of entity must be positive");
        }
    }
}
