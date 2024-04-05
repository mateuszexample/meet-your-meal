package pl.molsztyn.meetyourmeal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.molsztyn.meetyourmeal.api.dto.DeliveryAreaDTO;
import pl.molsztyn.meetyourmeal.api.dto.mapper.DeliveryAreaMapper;
import pl.molsztyn.meetyourmeal.business.DeliveryAreaService;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    static final String HOME = "/";
    static final String HOME_SEARCH_FOR_RESTAURANTS = "/search-for-restaurants";

    private final DeliveryAreaService deliveryAreaService;

    private final DeliveryAreaMapper deliveryAreaMapper;

    @RequestMapping(value = HOME, method = RequestMethod.GET)
    public ModelAndView home() {
        Map<String, ?> data = prepareDeliveryAreaToChoose();
        return new ModelAndView("home", data);
    }

    private Map<String, ?> prepareDeliveryAreaToChoose() {
        List<DeliveryAreaDTO> deliveryAreas = deliveryAreaService.findAll().stream()
                .map(deliveryAreaMapper::mapToDTO)
                .toList();

        Set<String> availableCountries = getAvailableCountries();
        Set<String> availableCities = getAvailableCities();
        Set<String> availablePostalCodes = getAvailablePostalCodes();
        Set<String> availableStreets = getAvailableStreets();

        return Map.of(
                "availableCountries", availableCountries,
                "availableCities", availableCities,
                "availablePostalCodes", availablePostalCodes,
                "availableStreets", availableStreets,
                "deliveryAreaDTO", new DeliveryAreaDTO()
        );
    }

    @PostMapping(HOME_SEARCH_FOR_RESTAURANTS)
    public String searchForRestaurantsNearYou(
            @ModelAttribute("deliveryAreaDTO") DeliveryAreaDTO deliveryAreaDTO,
            ModelMap modelMap
    ) {
        modelMap.addAllAttributes(prepareDeliveryAreaToChoose());
        return "redirect:/";
    }

    private Set<String> getAvailableCountries() {
        return deliveryAreaService.findAll().stream()
                .map(DeliveryArea::getCountry)
                .collect(Collectors.toCollection(() -> new TreeSet<String>(Comparator.naturalOrder())));
    }

    private Set<String> getAvailableCities() {
        return deliveryAreaService.findAll().stream()
                .map(DeliveryArea::getCity)
                .collect(Collectors.toCollection(() -> new TreeSet<String>(Comparator.naturalOrder())));
    }

    private Set<String> getAvailablePostalCodes() {
        return deliveryAreaService.findAll().stream()
                .map(DeliveryArea::getPostalCode)
                .collect(Collectors.toCollection(() -> new TreeSet<String>(Comparator.naturalOrder())));
    }

    private Set<String> getAvailableStreets() {
        return deliveryAreaService.findAll().stream()
                .map(DeliveryArea::getStreet)
                .collect(Collectors.toCollection(() -> new TreeSet<String>(Comparator.naturalOrder())));
    }
}
