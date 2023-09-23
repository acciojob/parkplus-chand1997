package com.driver.services.impl;

import com.driver.exceptions.InsufficientAmountException;
import com.driver.exceptions.PaymentModeNotDetectedException;
import com.driver.exceptions.ReservationNotFoundException;
import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) {
       Optional<Reservation> optionalReservation=reservationRepository2.findById(reservationId);
       if(!optionalReservation.isPresent()) throw new ReservationNotFoundException("invalid reservationId");

       Reservation reservation=optionalReservation.get();
       int bill=reservation.getNumberOfHours()*reservation.getSpot().getPricePerHour();
       if(amountSent<bill)  throw new InsufficientAmountException("Insufficient Amount");

       Payment payment=new Payment();

       if(mode.equals("cash")) payment.setPaymentMode(PaymentMode.CASH);
       else if(mode.equals("card")) payment.setPaymentMode(PaymentMode.CARD);
       else if(mode.equals("upi")) payment.setPaymentMode(PaymentMode.UPI);
       else throw new PaymentModeNotDetectedException("Payment mode not detected");

       payment.setReservation(reservation);
       payment.setPaymentCompleted(true);

      Payment savedPayment =paymentRepository2.save(payment);
      reservation.setPayment(savedPayment);
      reservationRepository2.save(reservation);

      return savedPayment;

    }
}
