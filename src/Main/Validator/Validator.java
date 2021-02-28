package Main.Validator;

import Main.Exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
