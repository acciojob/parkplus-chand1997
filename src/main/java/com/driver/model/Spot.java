package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Enumerated(EnumType.STRING)
    SpotType spotType;

    int pricePerHour;

    Boolean occupied;

    @ManyToOne
    @JoinColumn
    ParkingLot parkingLot;

    @OneToMany(mappedBy = "spot",cascade = CascadeType.ALL)
    List<Reservation> reservationList=new ArrayList<>();

    public Spot() {
    }

    public Spot(SpotType spotType, int pricePerHour, Boolean occupied) {
        this.spotType = spotType;
        this.pricePerHour = pricePerHour;
        this.occupied = occupied;
    }

    public int getId() {
        return id;
    }



    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
