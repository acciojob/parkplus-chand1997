package com.driver.exceptions;

public class PaymentModeNotDetectedException extends  RuntimeException{
    public PaymentModeNotDetectedException(String m){
        super(m);
    }
}
