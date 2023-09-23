package com.driver.repository;

import com.driver.model.Spot;
import com.driver.model.SpotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer>{

    @Query(value = "select spot from Spot spot where spot.parkingLot.id=:parkingLotId  order by spot.pricePerHour asc limit 1")
    Spot findSpot(Integer parkingLotId);

    

    @Query(value = "select spot from Spot spot where spot.parkingLot.id=:parkingLotId and (spot.spotType=:spotType or spot.spotType=:spotType1) order by spot.pricePerHour asc limit 1")
    Spot findSpotForFourOrAboveWheelers(Integer parkingLotId, SpotType spotType, SpotType spotType1);

    @Query(value = "select spot from Spot spot where spot.parkingLot.id=:parkingLotId and spot.spotType=:spotType  order by spot.pricePerHour asc limit 1")
    Spot findSpotForAboveFourWheelers(Integer parkingLotId, SpotType spotType);
}
