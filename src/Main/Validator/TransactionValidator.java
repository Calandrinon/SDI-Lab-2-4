package Main.Validator;

import Main.Exceptions.ValidationException;
import Main.Model.Transaction;

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
                .filter(e -> e.getQuantity() > 0)
                .orElseThrow(() -> new ValidationException("the quantity cannot be negative"));
    }
}
