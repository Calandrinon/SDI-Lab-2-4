package Validator;

import Model.Record;

import Exceptions.ValidationException;

public class RecordValidator implements Validator<Record> {

    /**
     * validate the given record
     *
     * @param entity must be valid
     *
     * @throws Exception
     *             if the record has a negative price
     *             if the record has a negative number of records
     */

    @Override
    public void validate(Record entity) throws Exception {
        if(entity.getPrice() < 0){
            throw new ValidationException("the price is negative");
        }
        if(entity.getInStock() < 0){
            throw new ValidationException("the number of records is negative");
        }
    }
}
