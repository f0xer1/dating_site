package com.ltp.contacts.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super("The id '" + id + "' does not exist in our records");
    }
    
}
