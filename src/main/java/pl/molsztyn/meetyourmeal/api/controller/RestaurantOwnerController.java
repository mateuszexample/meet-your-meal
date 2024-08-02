package pl.molsztyn.meetyourmeal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.molsztyn.meetyourmeal.api.dto.DeliveryAreaRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.FoodCategoryRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.RestaurantCreateRequestDTO;
import pl.molsztyn.meetyourmeal.api.dto.mapper.DeliveryAreaMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.FoodMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.RestaurantMapper;
import pl.molsztyn.meetyourmeal.business.RestaurantDashboardService;
import pl.molsztyn.meetyourmeal.domain.DeliveryAreaRequest;
import pl.molsztyn.meetyourmeal.domain.FoodCategoryRequest;
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
    private static final String OWNER_FOOD_CATEGORIES = "/owner-dashboard/food-categories";
    private static final String OWNER_FOOD_CATEGORIES_ADD = "/owner-dashboard/food-categories/add";
    private static final String OWNER_FOOD_CATEGORIES_DELETE = "/owner-dashboard/food-categories/delete/{id}";

    private final RestaurantDashboardService restaurantDashboardService;

    private final RestaurantMapper restaurantMapper;
    private final DeliveryAreaMapper deliveryAreaMapper;
    private final FoodMapper foodMapper;


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
        var deliveryAreas = restaurantDashboardService.getDeliveryAreas(authentication).stream()
                .map(deliveryAreaMapper::mapToDTO)
                .collect(Collectors.toSet());

        model.addAttribute("deliveryAreaDTOs", deliveryAreas);

        return "owner_dashboard#delivery_areas";
    }

    @GetMapping(value = OWNER_DELIVERY_AREAS_ADD)
    public String showAddFormDeliveryArea(Model model) {
        model.addAttribute("deliveryAreaRequestDTO", new DeliveryAreaRequestDTO());
        return "owner_dashboard#delivery_areas_add_form";
    }

    @PostMapping(OWNER_DELIVERY_AREAS_ADD)
    public String addDeliveryArea(
            @ModelAttribute("deliveryAreaRequestDTO") DeliveryAreaRequestDTO deliveryAreaRequestDTO,
            Authentication authentication
    ) {
        DeliveryAreaRequest deliveryArea = deliveryAreaMapper.mapFromDTO(deliveryAreaRequestDTO);
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

    @GetMapping(value = OWNER_FOOD_CATEGORIES)
    public String ownerFoodCategoryPage(Model model, Authentication authentication) {
        var foodCategories = restaurantDashboardService.getFoodCategories(authentication).stream()
                .map(foodMapper::mapToDTO)
                .collect(Collectors.toSet());

        model.addAttribute("foodCategoryDTOs", foodCategories);

        return "owner_dashboard#food_categories";
    }

    @GetMapping(value = OWNER_FOOD_CATEGORIES_ADD)
    public String showAddFormFoodCategory(Model model) {
        model.addAttribute("foodCategoryRequestDTO", new FoodCategoryRequestDTO());
        return "owner_dashboard#food_categories_add_form";
    }

    @PostMapping(OWNER_FOOD_CATEGORIES_ADD)
    public String addFoodCategory(
            @ModelAttribute("foodCategoryRequestDTO") FoodCategoryRequestDTO foodCategoryRequestDTO,
            Authentication authentication
    ) {
        FoodCategoryRequest foodCategoryRequest = foodMapper.mapFromDTO(foodCategoryRequestDTO);
        restaurantDashboardService.addFoodCategory(foodCategoryRequest, authentication);

        return "redirect:/owner-dashboard/food-categories";
    }

    @DeleteMapping(OWNER_FOOD_CATEGORIES_DELETE)
    public String deleteFoodCategory(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        restaurantDashboardService.deleteFoodCategory(id, authentication);

        return "redirect:/owner-dashboard/food-categories";
    }
}
