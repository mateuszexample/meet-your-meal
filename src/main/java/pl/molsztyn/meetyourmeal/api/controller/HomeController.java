package pl.molsztyn.meetyourmeal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.molsztyn.meetyourmeal.api.dto.DeliveryAreaRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantDTO;
import pl.molsztyn.meetyourmeal.api.dto.mapper.DeliveryAreaMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.RestaurantMapper;
import pl.molsztyn.meetyourmeal.business.DeliveryAreaService;
import pl.molsztyn.meetyourmeal.business.RestaurantService;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;
import pl.molsztyn.meetyourmeal.domain.DeliveryAreaRequest;
import pl.molsztyn.meetyourmeal.domain.Restaurant;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    static final String HOME = "/";
    static final String HOME_SEARCH_FOR_RESTAURANTS = "/search-for-restaurants";
    private static final String HOME_SEARCH_FOR_RESTAURANTS_RESTAURANT_IMAGE = "/search-for-restaurants/image/{name}";


    private final DeliveryAreaService deliveryAreaService;
    private final RestaurantService restaurantService;

    private final DeliveryAreaMapper deliveryAreaMapper;
    private final RestaurantMapper restaurantMapper;

    @RequestMapping(value = HOME, method = RequestMethod.GET)
    public String home(Model model) {

        model.addAttribute("availableCountries", getAvailableCountries());
        model.addAttribute("deliveryAreaRequestDTO", new DeliveryAreaRequestDTO());

        return "home";
    }

    private Set<String> getAvailableCountries() {
        return deliveryAreaService.findAll().stream()
                .map(DeliveryArea::getCountry)
                .collect(Collectors.toCollection(() -> new TreeSet<String>(Comparator.naturalOrder())));
    }

    @RequestMapping(value = HOME_SEARCH_FOR_RESTAURANTS, method = RequestMethod.GET)
    public String searchForRestaurantsNearYou(
            @ModelAttribute("deliveryAreaRequestDTO") DeliveryAreaRequestDTO deliveryAreaRequestDTO,
            ModelMap modelMap
    ) {

        DeliveryAreaRequest deliveryAreaRequest = deliveryAreaMapper.mapFromDTO(deliveryAreaRequestDTO);
        Integer deliveryAreaId = deliveryAreaService.findDeliveryAreaId(deliveryAreaRequest);

        List<Restaurant> restaurantList = restaurantService.getRestaurantsByDeliveryAreaId(deliveryAreaId);

        List<RestaurantDTO> restaurantDTOList = restaurantList.stream()
                .map(restaurantMapper::mapToDTO)
                .toList();

        modelMap.addAttribute("restaurantDTOs", restaurantDTOList);

        return "restaurants_near_you";
    }

    @GetMapping(HOME_SEARCH_FOR_RESTAURANTS_RESTAURANT_IMAGE)
    public ResponseEntity<byte[]> getRestaurantImage(@PathVariable("name") String restaurantName) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);
        byte[] imageData = restaurant.getImageData();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }
}

