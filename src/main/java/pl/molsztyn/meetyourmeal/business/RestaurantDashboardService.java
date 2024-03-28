package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.api.security.MeetYourMealUserDetailsService;
import pl.molsztyn.meetyourmeal.domain.Address;
import pl.molsztyn.meetyourmeal.domain.Restaurant;
import pl.molsztyn.meetyourmeal.domain.RestaurantCreateRequest;
import pl.molsztyn.meetyourmeal.domain.RestaurantOwner;

@Service
@AllArgsConstructor
public class RestaurantDashboardService {

    private final MeetYourMealUserDetailsService meetYourMealUserDetailsService;
    private final RestaurantOwnerService restaurantOwnerService;
    private final RestaurantService restaurantService;

    @Transactional
    public void create(RestaurantCreateRequest request, Authentication authentication) {
        String username = authentication.getName();
        Integer userId = meetYourMealUserDetailsService.getUserId(username);
        RestaurantOwner restaurantOwner = restaurantOwnerService.findRestaurantOwner(userId);

        Address address = buildAddress(request);
        Restaurant restaurant = buildRestaurant(request, restaurantOwner, address);

        restaurantService.createRestaurant(restaurant);
    }

    private Restaurant buildRestaurant(
            RestaurantCreateRequest request,
            RestaurantOwner restaurantOwner,
            Address address
    ) {
        return Restaurant.builder()
                .name(request.getName())
                .restaurantOwner(restaurantOwner)
                .address(address)
                .build();
    }

    private Address buildAddress(RestaurantCreateRequest request) {
        return Address.builder()
                .country(request.getRestaurantCountry())
                .city(request.getRestaurantCity())
                .postalCode(request.getRestaurantPostalCode())
                .street(request.getRestaurantStreet())
                .buildingNumber(request.getRestaurantBuildingNumber())
                .unitNumber(request.getRestaurantUnitNumber())
                .build();
    }

}
