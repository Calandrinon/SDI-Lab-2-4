package Validator;

import Exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
