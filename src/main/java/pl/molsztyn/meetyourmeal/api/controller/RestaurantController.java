package pl.molsztyn.meetyourmeal.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.molsztyn.meetyourmeal.api.dto.CartItemDTO;
import pl.molsztyn.meetyourmeal.api.dto.FoodCategoryDTO;
import pl.molsztyn.meetyourmeal.api.dto.FoodDTO;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantDTO;
import pl.molsztyn.meetyourmeal.api.dto.mapper.FoodMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.OrderMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.RestaurantMapper;
import pl.molsztyn.meetyourmeal.business.CartService;
import pl.molsztyn.meetyourmeal.business.FoodService;
import pl.molsztyn.meetyourmeal.business.RestaurantService;
import pl.molsztyn.meetyourmeal.domain.Cart;
import pl.molsztyn.meetyourmeal.domain.Customer;
import pl.molsztyn.meetyourmeal.domain.Food;
import pl.molsztyn.meetyourmeal.domain.Restaurant;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class RestaurantController {
    private static final String RESTAURANT_DETAILS = "/restaurant-details/{name}/menu/category/{category}";
    private static final String RESTAURANT_DETAILS_FOOD_IMAGE = "/restaurant-details/{name}/menu/image/{id}";
    private static final String RESTAURANTS_RESTAURANT_IMAGE = "/restaurant-details/{name}/image";

    private final RestaurantService restaurantService;
    private final FoodService foodService;

    private final CartService cartService;
    private final OrderMapper orderMapper;

    private final RestaurantMapper restaurantMapper;
    private final FoodMapper foodMapper;

    @GetMapping(RESTAURANT_DETAILS)
    public String showRestaurantDetails(
            @PathVariable("name") String restaurantName,
            @PathVariable("category") String category,
            @RequestParam(value = "cartId", required = false) Integer cartId,
            @RequestParam(value = "cartModal", required = false) String cartModal,
            Authentication authentication,
            Model model
    ) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);
        RestaurantDTO restaurantDTO = restaurantMapper.mapToDTO(restaurant);

        Set<FoodCategoryDTO> foodCategories = restaurant.getMenu().stream()
                .map(Food::getFoodCategory)
                .map(foodMapper::mapToDTO)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(FoodCategoryDTO::getCategory))));

        List<FoodDTO> menuByCategory;

        if("All".equals(category)) {
            menuByCategory = restaurant.getMenu().stream()
                    .map(foodMapper::mapToDTO)
                    .sorted(Comparator.comparing(FoodDTO::getName))
                    .sorted(Comparator.comparing(FoodDTO::getCategory))
                    .toList();
        } else {
            menuByCategory = restaurant.getMenu().stream()
                    .filter(food -> category.equals(food.getFoodCategory().getCategory()))
                    .map(foodMapper::mapToDTO)
                    .sorted(Comparator.comparing(FoodDTO::getName))
                    .toList();
        }

        model.addAttribute("restaurantDTO", restaurantDTO);
        model.addAttribute("menuDTO", menuByCategory);
        model.addAttribute("foodCategories", foodCategories);
        model.addAttribute("selectedCategory", category);

        if(cartId != null) {
            Customer customer = cartService.getCurrentlyLoggedInCustomer(authentication);
            Cart cart = customer.getCarts().stream()
                    .filter(c -> cartId.equals(c.getCartId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Could not find cart by id: [%s]".formatted(cartId)));

            List<CartItemDTO> cartItems = cart.getCartItems().stream()
                    .map(orderMapper::mapToDTO)
                    .toList();

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("cartId", cartId);
            model.addAttribute("totalPrice", cart.getTotalPrice());
        }

        model.addAttribute("cartModal", cartModal != null && cartModal.equals("true"));

        return "restaurant_details#menu";
    }

    @GetMapping(RESTAURANTS_RESTAURANT_IMAGE)
    public ResponseEntity<byte[]> getRestaurantImage(@PathVariable("name") String restaurantName) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);
        byte[] imageData = restaurant.getImageData();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }

    @GetMapping(RESTAURANT_DETAILS_FOOD_IMAGE)
    public ResponseEntity<byte[]> getFoodImage(@PathVariable Integer id) {
        byte[] imageData = foodService.getFoodImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }
}
