package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.domain.*;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;
import pl.molsztyn.meetyourmeal.infrastructure.security.MeetYourMealUserDetailsService;

import java.util.List;
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
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);

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
        return getCurrentlyLoggedInRestaurantOwner(authentication).getRestaurant().getDeliveryAreas();
    }

    private RestaurantOwner getCurrentlyLoggedInRestaurantOwner(Authentication authentication) {
        String username = authentication.getName();
        Integer userId = meetYourMealUserDetailsService.getUserId(username);
        return restaurantOwnerService.findRestaurantOwner(userId);
    }

    public void addDeliveryArea(DeliveryAreaRequest deliveryAreaRequest, Authentication authentication) {
        Restaurant restaurant = getCurrentlyLoggedInRestaurantOwner(authentication).getRestaurant();
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
        Restaurant restaurant = getCurrentlyLoggedInRestaurantOwner(authentication).getRestaurant();
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
        return getCurrentlyLoggedInRestaurantOwner(authentication).getRestaurant().getFoodCategories();
    }

    public void addFoodCategory(FoodCategoryRequest foodCategoryRequest, Authentication authentication) {
        Restaurant restaurant = getCurrentlyLoggedInRestaurantOwner(authentication).getRestaurant();
        String foodCategoryToFind = foodCategoryRequest.getCategory();
        Optional<FoodCategory> foodCategoryOpt = foodService.findFoodCategory(foodCategoryToFind);
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
        Restaurant restaurant = getCurrentlyLoggedInRestaurantOwner(authentication).getRestaurant();
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

    public Set<Food> getMenu(Authentication authentication) {
        return getCurrentlyLoggedInRestaurantOwner(authentication).getRestaurant().getMenu();
    }

    public byte[] getFoodImage(Integer foodId, Authentication authentication) {
        return getMenu(authentication).stream()
                .filter(food -> food.getFoodId().equals(foodId))
                .findFirst()
                .map(Food::getImageData)
                .orElseThrow(() -> new RuntimeException("Food image not found"));
    }

    public List<FoodCategory> getAvailableFoodCategories() {
        return foodService.findAll();
    }

    public void addFood(FoodRequest foodRequest, Authentication authentication) {
        Restaurant restaurant = getCurrentlyLoggedInRestaurantOwner(authentication).getRestaurant();

        String category = foodRequest.getCategory();
        Optional<FoodCategory> foodCategoryOpt = foodService.findFoodCategory(category);
        if (foodCategoryOpt.isEmpty()) {
            throw new NotFoundException("Could not find food category by category: [%s]".formatted(category));
        }

        FoodCategory foodCategory = foodCategoryOpt.get();
        Food food = buildFood(restaurant, foodRequest, foodCategory);
        Set<Food> menu = restaurant.getMenu();
        menu.add(food);
        restaurantService.updateRestaurant(restaurant.withMenu(menu));
    }

    private Food buildFood(Restaurant restaurant, FoodRequest foodRequest, FoodCategory foodCategory) {
        return Food.builder()
                .name(foodRequest.getName())
                .price(foodRequest.getPrice())
                .description(foodRequest.getDescription())
                .imageData(foodRequest.getImageData())
                .foodCategory(foodCategory)
                .restaurant(restaurant)
                .build();
    }

}
