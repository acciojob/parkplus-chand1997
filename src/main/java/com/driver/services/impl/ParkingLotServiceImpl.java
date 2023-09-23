package com.driver.services.impl;

import com.driver.exceptions.ParkingLotNotFoundException;
import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setAddress(address);
        parkingLot.setName(name);

        ParkingLot savedParkingLot=parkingLotRepository1.save(parkingLot);

        return savedParkingLot;

    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
       Optional<ParkingLot>  optionalParkingLot=parkingLotRepository1.findById(parkingLotId);
       if(!optionalParkingLot.isPresent()) throw new ParkingLotNotFoundException("Invalid parkingLot id!!!");
       ParkingLot parkingLot=optionalParkingLot.get();
        Spot spot=new Spot();
        if(numberOfWheels<=2) spot.setSpotType(SpotType.TWO_WHEELER);
        else if(numberOfWheels>2 && numberOfWheels<=4) spot.setSpotType(SpotType.FOUR_WHEELER);
        else spot.setSpotType(SpotType.OTHERS);

        spot.setPricePerHour(pricePerHour);
        spot.setParkingLot(parkingLot);

        Spot savedSpot=spotRepository1.save(spot);

        parkingLot.getSpotList().add(savedSpot);

        parkingLotRepository1.save(parkingLot);

        return savedSpot;


    }

    @Override
    public void deleteSpot(int spotId) {
       Optional<Spot> optionalSpot=spotRepository1.findById(spotId);
       if(!optionalSpot.isPresent()) throw new ParkingLotNotFoundException("Invalid spot id!!");
        Spot spot = optionalSpot.get();
        ParkingLot parkingLot = spot.getParkingLot();
        spotRepository1.deleteById(spotId);
        parkingLotRepository1.save(parkingLot);

    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Optional<Spot> optionalSpot =spotRepository1.findById(spotId);
        if(!optionalSpot.isPresent()) throw new ParkingLotNotFoundException("invalid spot id");

        Optional<ParkingLot> optionalParkingLot =parkingLotRepository1.findById(parkingLotId);
        if(!optionalParkingLot.isPresent()) throw new ParkingLotNotFoundException("invalid parkingLot id");

        if(optionalSpot.get().getParkingLot().getId()!=parkingLotId)
            throw new ParkingLotNotFoundException("no such spot id in this parkingLot");

        Spot spot=optionalSpot.get();
        spot.setPricePerHour(pricePerHour);
        Spot savedSpot=spotRepository1.save(spot);
        optionalParkingLot.get().getSpotList().add(savedSpot);
        parkingLotRepository1.save(optionalParkingLot.get());

        return savedSpot;


    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
          parkingLotRepository1.deleteById(parkingLotId);
    }
}
