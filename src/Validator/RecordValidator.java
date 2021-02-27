package Validator;

import Model.Record;

public class RecordValidator implements Validator<Record> {
    /*
    * price: positive Integer
    * InStock: positive Integer
    * */

    @Override
    public void validate(Record entity) throws Exception {
        if(entity.getPrice() < 0){
            throw new Exception("the price is negative");
        }
        if(entity.getInStock() < 0){
            throw new Exception("the number of records is negative");
        }
    }
}
