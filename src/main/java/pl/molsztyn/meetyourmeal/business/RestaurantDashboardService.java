package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.domain.*;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;
import pl.molsztyn.meetyourmeal.infrastructure.security.MeetYourMealUserDetailsService;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RestaurantDashboardService {

    private final MeetYourMealUserDetailsService meetYourMealUserDetailsService;
    private final RestaurantOwnerService restaurantOwnerService;
    private final RestaurantService restaurantService;
    private final DeliveryAreaService deliveryAreaService;

    @Transactional
    public void create(RestaurantCreateRequest request, Authentication authentication) {
        String username = authentication.getName();
        Integer userId = meetYourMealUserDetailsService.getUserId(username);
        RestaurantOwner restaurantOwner = restaurantOwnerService.findRestaurantOwner(userId);

        Address address = buildAddress(request);
        Restaurant restaurant = buildRestaurant(request, address);
        RestaurantOwner restaurantOwnerWithRestaurant = restaurantOwner.withRestaurant(restaurant);

        restaurantOwnerService.createRestaurant(restaurantOwnerWithRestaurant);
    }

    private Restaurant buildRestaurant(
            RestaurantCreateRequest request,
            Address address
    ) {
        return Restaurant.builder()
                .name(request.getName())
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

    public Set<DeliveryArea> deliveryAreas(Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);
        return restaurantOwner.getRestaurant().getDeliveryAreas();
    }

    private RestaurantOwner getCurrentlyLoggedInRestaurantOwner(Authentication authentication) {
        String username = authentication.getName();
        Integer userId = meetYourMealUserDetailsService.getUserId(username);
        return restaurantOwnerService.findRestaurantOwner(userId);
    }

    public void addDeliveryArea(DeliveryAreaAddRequest deliveryAreaAddRequest, Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);
        Restaurant restaurant = restaurantOwner.getRestaurant();
//        DeliveryArea deliveryArea = buildDeliveryArea(deliveryAreaAddRequest);
        Optional<DeliveryArea> deliveryAreaOpt = deliveryAreaService.findDeliveryArea(deliveryAreaAddRequest);
        DeliveryArea deliveryArea = buildOrGetDeliveryArea(deliveryAreaAddRequest, deliveryAreaOpt);
        Set<DeliveryArea> deliveryAreas = restaurant.getDeliveryAreas();
        deliveryAreas.add(deliveryArea);
        restaurantService.updateRestaurant(restaurant.withDeliveryAreas(deliveryAreas));
    }

    private DeliveryArea buildOrGetDeliveryArea(
            DeliveryAreaAddRequest deliveryAreaAddRequest,
            Optional<DeliveryArea> deliveryAreaOpt) {
        return deliveryAreaOpt.orElseGet(() -> buildDeliveryArea(deliveryAreaAddRequest));
    }

    private DeliveryArea buildDeliveryArea(DeliveryAreaAddRequest deliveryAreaAddRequest) {
        return DeliveryArea.builder()
                .country(deliveryAreaAddRequest.getCountry())
                .city(deliveryAreaAddRequest.getCity())
                .postalCode(deliveryAreaAddRequest.getPostalCode())
                .street(deliveryAreaAddRequest.getStreet())
                .build();
    }

    public void deleteDeliveryArea(final Integer id, Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);
        Restaurant restaurant = restaurantOwner.getRestaurant();
        Set<DeliveryArea> deliveryAreas = restaurant.getDeliveryAreas();
        Optional<DeliveryArea> deliveryAreaToDelete = deliveryAreas.stream()
                .filter(deliveryArea -> id.equals(deliveryArea.getDeliveryAreaId()))
                .findFirst();

        if(deliveryAreaToDelete.isEmpty()) {
            throw new NotFoundException("Could not find delivery area by id: [%s]".formatted(id));
        }
        deliveryAreas.remove(deliveryAreaToDelete.get());

        restaurantService.updateRestaurant(restaurant.withDeliveryAreas(deliveryAreas));
    }
}
