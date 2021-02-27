package Validator;

import Model.User;

public class UserValidator implements Validator<User>{
    /*
    * first name : String, cannot be empty, shorter than 64 characters
    * last name : String, cannot be empty, shorter than 64 characters
    * */

    @Override
    public void validate(User entity) throws Exception {
        if(entity.getFirstName().isBlank()){
            throw new Exception("the firstname cannot be blank");
        }

        if(entity.getFirstName().length() > 64){
            throw new Exception("the firstname should be shorter than 64 characters");
        }

        if(entity.getLastName().isBlank()){
            throw new Exception("the lastname cannot be empty");
        }

        if(entity.getFirstName().length() > 64){
            throw new Exception("the lastname should be shorter than 64 characters");
        }
    }
}
