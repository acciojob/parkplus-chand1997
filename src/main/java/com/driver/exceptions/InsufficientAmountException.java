package com.driver.exceptions;

public class InsufficientAmountException extends RuntimeException{
    public  InsufficientAmountException(String m){
        super(m);
    }
}
