package Main.Validator;

import Main.Model.Record;

import Main.Exceptions.ValidationException;

import javax.swing.text.html.Option;
import java.util.Optional;

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
        Optional.ofNullable(entity).filter(e -> e.getPrice() > 0).orElseThrow(() -> new ValidationException("the price cannot be negative"));
        Optional.of(entity).filter(e -> e.getInStock() >= 0).orElseThrow(() -> new ValidationException("there are no more records in stock"));
        Optional.of(entity).filter(e -> !e.getAlbumName().isBlank()).orElseThrow(() -> new ValidationException("the album name cannot be empty"));
        Optional.ofNullable(entity).filter(e -> e.getId() > 0).orElseThrow(() -> new ValidationException("The id cannot be negative."));
    }
}
