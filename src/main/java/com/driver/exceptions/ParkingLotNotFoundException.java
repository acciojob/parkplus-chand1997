package com.driver.exceptions;

public class ParkingLotNotFoundException extends RuntimeException{

    public  ParkingLotNotFoundException(String message){
        super(message);
    }
}
