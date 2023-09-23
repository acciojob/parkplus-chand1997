package com.driver.exceptions;

public class ReservationNotFoundException extends RuntimeException{
    public  ReservationNotFoundException(String m){
        super(m);
    }
}
