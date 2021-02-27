package Validator;

public interface Validator<T> {
    void validate(T entity) throws Exception;
}
