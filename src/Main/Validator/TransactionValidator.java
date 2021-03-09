package Validator;

import Exceptions.ValidationException;
import Model.Transaction;

import java.util.Optional;

public class TransactionValidator implements Validator<Transaction> {
    /**
     *
     * @param entity given transaction
     * @throws ValidationException
     *                  if the quantity of the given entity is negative
     */

    @Override
    public void validate(Transaction entity) throws ValidationException {
        Optional.ofNullable(entity)
                .orElseThrow(() -> new ValidationException("entity is null"));

        Optional.of(entity)
                .filter(e -> e.getId() > 0)
                .orElseThrow(() -> new ValidationException("id of entity must be positive"));

        Optional.of(entity)
                .filter(e -> e.getQuantity() > 0)
                .orElseThrow(() -> new ValidationException("the quantity cannot be negative"));
    }
}
