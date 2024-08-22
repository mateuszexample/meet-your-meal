package pl.molsztyn.meetyourmeal.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantDTO;
import pl.molsztyn.meetyourmeal.api.dto.mapper.RestaurantMapper;
import pl.molsztyn.meetyourmeal.business.RestaurantService;
import pl.molsztyn.meetyourmeal.domain.Restaurant;

@Controller
@AllArgsConstructor
public class RestaurantController {

//    private static final String RESTAURANTS_RESTAURANT_IMAGE = "/search-for-restaurants/restaurant/image/{name}";
    private static final String RESTAURANTS_RESTAURANT_IMAGE = "/restaurant-details/image/{name}";

    private final RestaurantService restaurantService;

    private final RestaurantMapper restaurantMapper;

    @GetMapping("/restaurant-details/{name}")
    public String showRestaurantDetails(@PathVariable("name") String restaurantName, Model model) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);
        RestaurantDTO restaurantDTO = restaurantMapper.mapToDTO(restaurant);

        model.addAttribute("restaurantDTO", restaurantDTO);

        return "restaurant_details";
    }

    @GetMapping(RESTAURANTS_RESTAURANT_IMAGE)
    public ResponseEntity<byte[]> getRestaurantImage(@PathVariable("name") String restaurantName) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);
        byte[] imageData = restaurant.getImageData();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }
}
