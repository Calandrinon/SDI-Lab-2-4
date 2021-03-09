package Main.Validator;

import Main.Model.User;

import Main.Exceptions.ValidationException;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Predicate;

public class UserValidator implements Validator<User>{
    /**
     * validate the given user
     *
     * @param entity must be valid
     *
     * @throws ValidationException
     *             if the user has an empty firstname
     *             if the user has an empty lastname
     *             if the user has a firstname that is longer than 64 characters
     *             if the user has a lastname that is longer than 64 characters
     */

    @Override
    public void validate(User entity) throws ValidationException {
        Optional.ofNullable(entity)
                .orElseThrow(() -> new ValidationException("entity is null"));

        Optional.of(entity)
                .filter(e -> e.getId() > 0)
                .orElseThrow(() -> new ValidationException("id of entity must be positive"));

        Predicate<User> checkFirstName = e ->
                !e.getFirstName().isBlank()
                        && e.getFirstName().length() <= 64;

        Predicate<User> checkLastName = e ->
                !e.getLastName().isBlank()
                        && e.getLastName().length() <= 64;

        Optional.of(entity)
                .filter(checkFirstName)
                .orElseThrow(() -> new ValidationException("firstname is invalid"));

        Optional.of(entity)
                .filter(checkLastName)
                .orElseThrow(() -> new ValidationException("lastname is invalid"));
    }
}
