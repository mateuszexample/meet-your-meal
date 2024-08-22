package pl.molsztyn.meetyourmeal.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class RestaurantCreateRequest {

    String name;
    byte[] imageData;

    String restaurantCountry;
    String restaurantCity;
    String restaurantPostalCode;
    String restaurantStreet;
    String restaurantBuildingNumber;
    String restaurantUnitNumber;
}
