package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.domain.*;
import pl.molsztyn.meetyourmeal.domain.enums.OrderStatus;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;
import pl.molsztyn.meetyourmeal.infrastructure.security.MeetYourMealUserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantDashboardService {

    private final MeetYourMealUserDetailsService meetYourMealUserDetailsService;
    private final RestaurantOwnerService restaurantOwnerService;
    private final RestaurantService restaurantService;
    private final DeliveryAreaService deliveryAreaService;
    private final FoodService foodService;
    private final AddressService addressService;
    private final OrderService orderService;

    @Transactional
    public void create(RestaurantCreateRequest request, Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);

        Address address = buildAddress(request);
        Restaurant restaurant = buildRestaurant(request, address);
        RestaurantOwner restaurantOwnerWithRestaurant = restaurantOwner.withRestaurant(restaurant);

        restaurantOwnerService.createRestaurant(restaurantOwnerWithRestaurant);
    }

    @Transactional
    public void update(RestaurantOwner restaurantOwnerWithNewData) {
        restaurantOwnerService.createRestaurant(restaurantOwnerWithNewData);
    }

    @Transactional
    public void delete(Authentication authentication) {
        RestaurantOwner restaurantOwner = getCurrentlyLoggedInRestaurantOwner(authentication);
        Integer restaurantId = restaurantOwner.getRestaurant().getRestaurantId();

        RestaurantOwner restaurantOwnerWithoutRestaurant = restaurantOwner.withRestaurant(null);
        update(restaurantOwnerWithoutRestaurant);

        restaurantService.deleteRestaurant(restaurantId);

        cleanupUnusedFoodCategories();
        cleanupUnusedDeliveryAreas();
    }

    private void cleanupUnusedFoodCategories() {
        List<FoodCategory> unusedFoodCategories = foodService.findUnusedFoodCategories();
        Set<Integer> foodCategoriesToDelete = unusedFoodCategories.stream()
                .map(FoodCategory::getFoodCategoryId)
                .collect(Collectors.toSet());
        if (!unusedFoodCategories.isEmpty()) {
            foodService.deleteAllFoodCategoriesById(foodCategoriesToDelete);
        }
    }

    private void cleanupUnusedDeliveryAreas() {
        List<DeliveryArea> unusedDeliveryAreas = deliveryAreaService.findUnusedDeliveryAreas();
        Set<Integer> deliveryAreasToDelete = unusedDeliveryAreas.stream()
                .map(DeliveryArea::getDeliveryAreaId)
                .collect(Collectors.toSet());
        if (!unusedDeliveryAreas.isEmpty()) {
            deliveryAreaService.deleteAllDeliveryAreasById(deliveryAreasToDelete);
        }
    }

    private Restaurant buildRestaurant(
            RestaurantCreateRequest request,
            Address address
    ) {
        return Restaurant.builder()
                .name(request.getName())
                .imageData(request.getImageData())
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

    public RestaurantOwner getCurrentlyLoggedInRestaurantOwner(Authentication authentication) {
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
        cleanupUnusedDeliveryAreas();
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

        Set<Food> foodSetToDelete = restaurant.getMenu().stream()
                .filter(food -> foodCategoryToDelete.get().equals(food.getFoodCategory()))
                .collect(Collectors.toSet());
        Set<Integer> foodIdSetToDelete = restaurant.getMenu().stream()
                .filter(food -> foodCategoryToDelete.get().equals(food.getFoodCategory()))
                .map(Food::getFoodId)
                .collect(Collectors.toSet());
        Set<Food> menu = restaurant.getMenu();

        menu.removeAll(foodSetToDelete);
        foodCategories.remove(foodCategoryToDelete.get());

        restaurantService.updateRestaurant(restaurant.withFoodCategories(foodCategories));
        foodService.deleteAllFoodById(foodIdSetToDelete);
        cleanupUnusedFoodCategories();
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

    public Restaurant getRestaurant(Authentication authentication) {
        return getCurrentlyLoggedInRestaurantOwner(authentication).getRestaurant();
    }

    public void deleteOldAddress(Integer addressId) {
        addressService.deleteAddressById(addressId);
    }

    public void deleteFood(Integer id, Authentication authentication) {
        Restaurant restaurant = getCurrentlyLoggedInRestaurantOwner(authentication).getRestaurant();
        Set<Food> menu = restaurant.getMenu();
        Optional<Food> foodToDelete = menu.stream()
                .filter(food -> id.equals(food.getFoodId()))
                .findFirst();

        if (foodToDelete.isEmpty()) {
            throw new NotFoundException("Could not find food by id: [%s]".formatted(id));
        }
        menu.remove(foodToDelete.get());

        restaurantService.updateRestaurant(restaurant.withMenu(menu));
        foodService.deleteFood(foodToDelete.get().getFoodId());
    }

    public Set<OrderDetails> getAllOrdersForRestaurant(Authentication authentication) {
        Integer restaurantId = getRestaurant(authentication).getRestaurantId();
        return orderService.getAllOrdersForRestaurant(restaurantId);
    }

    public OrderStatus updateOrderStatus(String orderNumber, OrderStatus orderStatus, Authentication authentication) {
        Integer restaurantId = getRestaurant(authentication).getRestaurantId();
        Set<OrderDetails> allOrdersForRestaurant = orderService.getAllOrdersForRestaurant(restaurantId);

        Optional<OrderDetails> optionalOrderDetails = allOrdersForRestaurant.stream()
                .filter(orderDetails -> orderNumber.equals(orderDetails.getOrderNumber()))
                .findFirst();
        if (optionalOrderDetails.isEmpty()) {
            throw new NotFoundException("Could not find order by orderNumber: [%s]".formatted(orderNumber));
        }

        OrderDetails orderToUpdate = optionalOrderDetails.get();
        if (OrderStatus.CANCELLED.equals(orderStatus)) {
            orderService.update(orderToUpdate.withOrderStatus(OrderStatus.CANCELLED_BY_RESTAURANT));
        } else {
            orderService.update(orderToUpdate.withOrderStatus(orderStatus));
        }

        return orderToUpdate.getOrderStatus();
    }
}
