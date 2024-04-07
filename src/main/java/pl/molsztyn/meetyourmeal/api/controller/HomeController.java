package pl.molsztyn.meetyourmeal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.molsztyn.meetyourmeal.api.dto.DeliveryAreaDTO;
import pl.molsztyn.meetyourmeal.api.dto.mapper.DeliveryAreaMapper;
import pl.molsztyn.meetyourmeal.business.DeliveryAreaService;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    static final String HOME = "/";
    static final String DELIVERY_AREA = "/delivery-area";
    static final String HOME_SEARCH_FOR_RESTAURANTS = "/search-for-restaurants";

    private final DeliveryAreaService deliveryAreaService;

    private final DeliveryAreaMapper deliveryAreaMapper;

    @RequestMapping(value = HOME, method = RequestMethod.GET)
    public String home(Model model) {

        model.addAttribute("availableCountries", getAvailableCountries());
        model.addAttribute("deliveryAreaDTO", new DeliveryAreaDTO());

        return "home";
    }

    private Set<String> getAvailableCountries() {
        return deliveryAreaService.findAll().stream()
                .map(DeliveryArea::getCountry)
                .collect(Collectors.toCollection(() -> new TreeSet<String>(Comparator.naturalOrder())));
    }
}

//    @PostMapping(HOME_SEARCH_FOR_RESTAURANTS)
//    public String searchForRestaurantsNearYou(
//            @ModelAttribute("deliveryAreaDTO") DeliveryAreaDTO deliveryAreaDTO,
//            ModelMap modelMap
//    ) {
//        modelMap.addAllAttributes(prepareAvailableCountriesToChoose());
//        return "redirect:/";

//    }


