package pl.molsztyn.meetyourmeal.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantCreateRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.mapper.RestaurantCreateRequestMapper;
import pl.molsztyn.meetyourmeal.business.RestaurantService;
import pl.molsztyn.meetyourmeal.database.entity.AddressEntity;
import pl.molsztyn.meetyourmeal.database.entity.RestaurantEntity;
import pl.molsztyn.meetyourmeal.database.repository.jpa.RestaurantJpaRepository;
import pl.molsztyn.meetyourmeal.database.repository.jpa.RestaurantOwnerJpaRepository;

@Controller
//@RequiredArgsConstructor
@AllArgsConstructor
public class RestaurantOwnerController {
    private static final String OWNER = "/owner-dashboard";
    private static final String OWNER_RESTAURANT_DETAILS = "/owner-dashboard/restaurant-details";
    private static final String OWNER_RESTAURANT_DETAILS_ADD = "/owner-dashboard/restaurant-details/add";


    private final RestaurantService restaurantService;
    private final RestaurantCreateRequestMapper restaurantCreateRequestMapper;

    private final RestaurantOwnerJpaRepository restaurantOwnerJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;


    @GetMapping(value = OWNER)
    public String ownerDashboard() {
        return "owner_dashboard";
    }


    @GetMapping(value = OWNER_RESTAURANT_DETAILS)
    public String showRestaurantForm(Model model) {
        model.addAttribute("restaurantCreateRequestDTO", new RestaurantCreateRequestDTO());
        return "owner_dashboard#restaurant_details";
    }

    @PostMapping(OWNER_RESTAURANT_DETAILS_ADD)
    public String createRestaurant(
            @ModelAttribute("restaurantCreateRequestDTO") RestaurantCreateRequestDTO restaurantCreateRequestDTO
    ) {

        RestaurantEntity newRestaurant = RestaurantEntity.builder()
                .name(restaurantCreateRequestDTO.getName())
                .address(AddressEntity.builder()
                        .country(restaurantCreateRequestDTO.getRestaurantCountry())
                        .city(restaurantCreateRequestDTO.getRestaurantCity())
                        .postalCode(restaurantCreateRequestDTO.getRestaurantPostalCode())
                        .street(restaurantCreateRequestDTO.getRestaurantStreet())
                        .buildingNumber(restaurantCreateRequestDTO.getRestaurantBuildingNumber())
                        .unitNumber(restaurantCreateRequestDTO.getRestaurantUnitNumber())
                        .build())
                .restaurantOwner(restaurantOwnerJpaRepository.findByPesel("94051311769").get())
                .build();
        restaurantJpaRepository.save(newRestaurant);
//        Restaurant restaurant = restaurantCreateRequestMapper.map(restaurantCreateRequestDTO);
//        restaurantService.createRestaurant(restaurant);

        return "redirect:/owner-dashboard";
    }

}
