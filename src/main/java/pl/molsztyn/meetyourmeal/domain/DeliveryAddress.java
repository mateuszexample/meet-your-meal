package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"country", "city", "postalCode", "street"})
@ToString(of = {"deliveryAddressId", "country", "city", "postalCode", "street"})
public class DeliveryAddress {

    Integer deliveryAddressId;
    String country;
    String city;
    String postalCode;
    String street;
    String buildingNumber;
    String unitNumber;
}
