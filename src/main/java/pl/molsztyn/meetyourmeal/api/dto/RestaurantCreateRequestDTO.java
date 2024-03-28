package pl.molsztyn.meetyourmeal.api.dto;

import lombok.*;

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
}
