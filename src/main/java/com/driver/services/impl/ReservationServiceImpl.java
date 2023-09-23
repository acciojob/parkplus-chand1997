package com.driver.services.impl;

import com.driver.exceptions.CannotMakeReservationException;
import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours,
                                   Integer numberOfWheels) throws Exception {

       Optional<User>  optionalUser=userRepository3.findById(userId);
       if(!optionalUser.isPresent()) throw new CannotMakeReservationException("Invalid userId");

      Optional<ParkingLot> optionalParkingLot=parkingLotRepository3.findById(parkingLotId);
      if(!optionalParkingLot.isPresent()) throw new CannotMakeReservationException("Invalid parkingLot Id");

          Spot spot=null;
        if(numberOfWheels<=2) {
             spot=spotRepository3.findSpot(parkingLotId);

        }
        else if(numberOfWheels>2 && numberOfWheels<=4) {
             spot =spotRepository3.findSpotForFourOrAboveWheelers(parkingLotId,SpotType.FOUR_WHEELER,SpotType.OTHERS);
        }
        else{
             spot=spotRepository3.findSpotForAboveFourWheelers(parkingLotId,SpotType.OTHERS);
        }

        if(spot==null) throw new CannotMakeReservationException("Spot not available");


        User user=optionalUser.get();
        Reservation reservation=new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setSpot(spot);
        reservation.setUser(user);

        Reservation savedReservation=reservationRepository3.save(reservation);

        spot.getReservationList().add(savedReservation);
        user.getReservationList().add(savedReservation);

        spotRepository3.save(spot);
        userRepository3.save(user);

        return savedReservation;










    }
}
