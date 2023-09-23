package com.driver.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String m){
        super(m);
    }
}
