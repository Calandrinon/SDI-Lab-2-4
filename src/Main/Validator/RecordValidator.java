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
        if (entity.getPrice() < 0){
            throw new ValidationException("The price cannot be negative.");
        }

        if (entity.getInStock() < 0){
            throw new ValidationException("The number of records cannot be negative.");
        }

        if (entity.getAlbumName().equals("")) {
            throw new ValidationException("The album name cannot be an empty string.");
        }
    }
}
