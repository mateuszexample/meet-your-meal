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
    private final FoodService foodService;

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

    public Set<DeliveryArea> getDeliveryAreas(Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);
        return restaurantOwner.getRestaurant().getDeliveryAreas();
    }

    private RestaurantOwner getCurrentlyLoggedInRestaurantOwner(Authentication authentication) {
        String username = authentication.getName();
        Integer userId = meetYourMealUserDetailsService.getUserId(username);
        return restaurantOwnerService.findRestaurantOwner(userId);
    }

    public void addDeliveryArea(DeliveryAreaRequest deliveryAreaRequest, Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);
        Restaurant restaurant = restaurantOwner.getRestaurant();
        Optional<DeliveryArea> deliveryAreaOpt = deliveryAreaService.findDeliveryArea(deliveryAreaRequest);
        DeliveryArea deliveryArea = deliveryAreaOpt.orElseGet(() -> buildDeliveryArea(deliveryAreaRequest));
        Set<DeliveryArea> deliveryAreas = restaurant.getDeliveryAreas();
        deliveryAreas.add(deliveryArea);
        restaurantService.updateRestaurant(restaurant.withDeliveryAreas(deliveryAreas));
    }

    private DeliveryArea buildDeliveryArea(DeliveryAreaRequest deliveryAreaRequest) {
        return DeliveryArea.builder()
                .country(deliveryAreaRequest.getCountry())
                .city(deliveryAreaRequest.getCity())
                .postalCode(deliveryAreaRequest.getPostalCode())
                .street(deliveryAreaRequest.getStreet())
                .build();
    }

    public void deleteDeliveryArea(final Integer id, Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);
        Restaurant restaurant = restaurantOwner.getRestaurant();
        Set<DeliveryArea> deliveryAreas = restaurant.getDeliveryAreas();
        Optional<DeliveryArea> deliveryAreaToDelete = deliveryAreas.stream()
                .filter(deliveryArea -> id.equals(deliveryArea.getDeliveryAreaId()))
                .findFirst();

        if (deliveryAreaToDelete.isEmpty()) {
            throw new NotFoundException("Could not find delivery area by id: [%s]".formatted(id));
        }
        deliveryAreas.remove(deliveryAreaToDelete.get());

        restaurantService.updateRestaurant(restaurant.withDeliveryAreas(deliveryAreas));
    }

    public Set<FoodCategory> getFoodCategories(Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);
        return restaurantOwner.getRestaurant().getFoodCategories();
    }

    public void addFoodCategory(FoodCategoryRequest foodCategoryRequest, Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);
        Restaurant restaurant = restaurantOwner.getRestaurant();
        Optional<FoodCategory> foodCategoryOpt = foodService.findFoodCategory(foodCategoryRequest);
        FoodCategory foodCategory = foodCategoryOpt.orElseGet(() -> buildFoodCategory(foodCategoryRequest));
        Set<FoodCategory> foodCategories = restaurant.getFoodCategories();
        foodCategories.add(foodCategory);
        restaurantService.updateRestaurant(restaurant.withFoodCategories(foodCategories));
    }

    private FoodCategory buildFoodCategory(FoodCategoryRequest foodCategoryRequest) {
        return FoodCategory.builder()
                .category(foodCategoryRequest.getCategory())
                .build();
    }

    public void deleteFoodCategory(Integer id, Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);
        Restaurant restaurant = restaurantOwner.getRestaurant();
        Set<FoodCategory> foodCategories = restaurant.getFoodCategories();
        Optional<FoodCategory> foodCategoryToDelete = foodCategories.stream()
                .filter(foodCategory -> id.equals(foodCategory.getFoodCategoryId()))
                .findFirst();

        if (foodCategoryToDelete.isEmpty()) {
            throw new NotFoundException("Could not find food category by id: [%s]".formatted(id));
        }
        foodCategories.remove(foodCategoryToDelete.get());

        restaurantService.updateRestaurant(restaurant.withFoodCategories(foodCategories));
    }
}
