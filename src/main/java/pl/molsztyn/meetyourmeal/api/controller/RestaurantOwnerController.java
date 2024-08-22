package pl.molsztyn.meetyourmeal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.molsztyn.meetyourmeal.api.dto.*;
import pl.molsztyn.meetyourmeal.api.dto.mapper.DeliveryAreaMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.FoodMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.RestaurantMapper;
import pl.molsztyn.meetyourmeal.business.RestaurantDashboardService;
import pl.molsztyn.meetyourmeal.domain.DeliveryAreaRequest;
import pl.molsztyn.meetyourmeal.domain.FoodCategoryRequest;
import pl.molsztyn.meetyourmeal.domain.FoodRequest;
import pl.molsztyn.meetyourmeal.domain.RestaurantCreateRequest;

import java.io.IOException;
import java.util.List;
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
    private static final String OWNER_MENU = "/owner-dashboard/menu";
    private static final String OWNER_MENU_FOOD_ADD = "/owner-dashboard/menu/add";
    private static final String OWNER_MENU_FOOD_DELETE = "/owner-dashboard/menu/delete/{id}";
    private static final String OWNER_MENU_FOOD_IMAGE = "/owner-dashboard/menu/image/{id}";

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
            @RequestParam("image") MultipartFile image,
            Authentication authentication
    ) throws IOException {
        RestaurantCreateRequest request = restaurantMapper.mapFromDTO(restaurantCreateRequestDTO);

        // image upload
        if(!image.isEmpty()) {
            byte[] imageData = image.getBytes();
            request = request.withImageData(imageData);
        }

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
    @GetMapping(value = OWNER_MENU)
    public String ownerMenuPage(Model model, Authentication authentication) {
        var menu = restaurantDashboardService.getMenu(authentication).stream()
                .map(foodMapper::mapToDTO)
                .collect(Collectors.toSet());

        model.addAttribute("foodDTOs", menu);

        return "owner_dashboard#menu";
    }

    @GetMapping(value = OWNER_MENU_FOOD_ADD)
    public String showAddFormFood(Model model) {

        List<String> availableFoodCategories = restaurantDashboardService.getAvailableFoodCategories().stream()
                .map(foodMapper::mapToDTO)
                .map(FoodCategoryDTO::getCategory)
                .toList();

        model.addAttribute("foodRequestDTO", new FoodRequestDTO());
        model.addAttribute("availableFoodCategoryDTOs", availableFoodCategories);

        return "owner_dashboard#menu_food_add_form";
    }

    @PostMapping(OWNER_MENU_FOOD_ADD)
    public String addFood(
            @ModelAttribute("foodRequestDTO") FoodRequestDTO foodRequestDTO,
            @RequestParam("image") MultipartFile image,
            Authentication authentication
    ) throws IOException {
        FoodRequest foodRequest = foodMapper.mapFromDTO(foodRequestDTO);

        // image upload
        if(!image.isEmpty()) {
            byte[] imageData = image.getBytes();
            foodRequest = foodRequest.withImageData(imageData);
        }

        restaurantDashboardService.addFood(foodRequest, authentication);

        return "redirect:/owner-dashboard/menu";
    }

    @GetMapping(OWNER_MENU_FOOD_IMAGE)
    public ResponseEntity<byte[]> getFoodImage(@PathVariable Integer id, Authentication authentication) {
        byte[] imageData = restaurantDashboardService.getFoodImage(id, authentication);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }

//    @DeleteMapping(OWNER_MENU_FOOD_DELETE)
//    public String deleteFood(
//            @PathVariable Integer id,
//            Authentication authentication
//    ) {
//        restaurantDashboardService.deleteFood(id, authentication);
//
//        return "redirect:/owner-dashboard/food-categories";
//    }
}
