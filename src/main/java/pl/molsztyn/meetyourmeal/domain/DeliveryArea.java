package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = {"country", "city", "postalCode", "street"})
@ToString(of = {"deliveryAreaId", "country", "city", "postalCode", "street"})
public class DeliveryArea {

    Integer deliveryAreaId;
    String country;
    String city;
    String postalCode;
    String street;
}
