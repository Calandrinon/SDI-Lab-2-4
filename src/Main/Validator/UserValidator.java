package Main.Validator;

import Main.Model.User;

import Main.Exceptions.ValidationException;

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
        if(entity.getFirstName().isBlank()){
            throw new ValidationException("the firstname cannot be blank");
        }

        if(entity.getFirstName().length() > 64){
            throw new ValidationException("the firstname should be shorter than 64 characters");
        }

        if(entity.getLastName().isBlank()){
            throw new ValidationException("the lastname cannot be empty");
        }

        if(entity.getFirstName().length() > 64){
            throw new ValidationException("the lastname should be shorter than 64 characters");
        }
    }
}
