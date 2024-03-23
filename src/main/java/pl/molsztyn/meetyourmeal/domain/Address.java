package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "addressId")
@ToString(of = {"addressId", "country", "city", "postalCode", "street"})
public class Address {

    Integer addressId;
    String country;
    String city;
    String postalCode;
    String street;
    String buildingNumber;
    String unitNumber;
    Customer customer;
    Restaurant restaurant;
}

