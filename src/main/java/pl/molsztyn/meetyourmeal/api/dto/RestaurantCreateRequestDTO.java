package pl.molsztyn.meetyourmeal.api.dto;

import lombok.*;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;
import pl.molsztyn.meetyourmeal.domain.RestaurantOwner;

import java.util.Set;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCreateRequestDTO {

    private String name;

    private String restaurantCountry;
    private String restaurantCity;
    private String restaurantPostalCode;
    private String restaurantStreet;
    private String restaurantBuildingNumber;
    private String restaurantUnitNumber;

//    private Set<DeliveryArea> deliveryAreas;
}
