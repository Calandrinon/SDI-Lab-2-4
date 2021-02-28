package Main.Validator;

import Main.Model.Record;

import Main.Exceptions.ValidationException;

public class RecordValidator implements Validator<Record> {

    /**
     * validate the given record
     *
     * @param entity must be valid
     *
     * @throws ValidationException
     *             if the record has a negative price
     *             if the record has a negative number of records
     */

    @Override
    public void validate(Record entity) throws ValidationException {
        if(entity.getPrice() < 0){
            throw new ValidationException("the price is negative");
        }
        if(entity.getInStock() < 0){
            throw new ValidationException("the number of records is negative");
        }
    }
}
