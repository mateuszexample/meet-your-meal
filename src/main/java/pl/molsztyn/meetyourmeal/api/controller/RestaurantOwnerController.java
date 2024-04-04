package pl.molsztyn.meetyourmeal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.molsztyn.meetyourmeal.api.dto.DeliveryAreaAddRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantCreateRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.mapper.DeliveryAreaMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.RestaurantMapper;
import pl.molsztyn.meetyourmeal.business.RestaurantDashboardService;
import pl.molsztyn.meetyourmeal.domain.DeliveryAreaAddRequest;
import pl.molsztyn.meetyourmeal.domain.RestaurantCreateRequest;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RestaurantOwnerController {
    private static final String OWNER = "/owner-dashboard";
    private static final String OWNER_RESTAURANT_DETAILS = "/owner-dashboard/restaurant-details";
    private static final String OWNER_RESTAURANT_DETAILS_ADD = "/owner-dashboard/restaurant-details/add";
    private static final String OWNER_DELIVERY_AREAS = "/owner-dashboard/delivery-areas";
    private static final String OWNER_DELIVERY_AREAS_ADD = "/owner-dashboard/delivery-areas/add";
    private static final String OWNER_DELIVERY_AREAS_DELETE = "/owner-dashboard/delivery-areas/delete/{id}";

    private final RestaurantDashboardService restaurantDashboardService;

    private final RestaurantMapper restaurantMapper;
    private final DeliveryAreaMapper deliveryAreaMapper;


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
            @ModelAttribute("restaurantCreateRequestDTO") RestaurantCreateRequestDTO restaurantCreateRequestDTO,
            Authentication authentication
    ) {
        RestaurantCreateRequest request = restaurantMapper.mapFromDTO(restaurantCreateRequestDTO);
        restaurantDashboardService.create(request, authentication);

        return "redirect:/owner-dashboard";
    }

    @GetMapping(value = OWNER_DELIVERY_AREAS)
    public String ownerDeliveryAreasPage(Model model, Authentication authentication) {
        var deliveryAreas = restaurantDashboardService.deliveryAreas(authentication).stream()
                .map(deliveryAreaMapper::mapToDTO)
                .collect(Collectors.toSet());

        model.addAttribute("deliveryAreaDTOs", deliveryAreas);

        return "owner_dashboard#delivery_areas";
    }


    @GetMapping(value = OWNER_DELIVERY_AREAS_ADD)
    public String showAddFormDeliveryArea(Model model) {
        model.addAttribute("deliveryAreaAddRequestDTO", new DeliveryAreaAddRequestDTO());
        return "owner_dashboard#delivery_areas_add_form";
    }

    @PostMapping(OWNER_DELIVERY_AREAS_ADD)
    public String addDeliveryArea(
            @ModelAttribute("deliveryAreaDTOs") DeliveryAreaAddRequestDTO deliveryAreaAddRequestDTO,
            Authentication authentication
    ) {
        DeliveryAreaAddRequest deliveryArea = deliveryAreaMapper.mapFromDTO(deliveryAreaAddRequestDTO);
        restaurantDashboardService.addDeliveryArea(deliveryArea, authentication);

        return "redirect:/owner-dashboard/delivery-areas";
    }

    @DeleteMapping(OWNER_DELIVERY_AREAS_DELETE)
    public String deleteDeliveryArea(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        restaurantDashboardService.deleteDeliveryArea(id, authentication);

        return "redirect:/owner-dashboard/delivery-areas";
    }

}
