package pl.molsztyn.meetyourmeal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.molsztyn.meetyourmeal.business.DeliveryAreaService;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class DeliveryAreaController {

    private final DeliveryAreaService deliveryAreaService;

    @GetMapping("/fetch-cities")
    @ResponseBody
    public Set<String> fetchCitiesForCountry(
            @RequestParam(value = "country") String country
    ) {
        return deliveryAreaService.findCitiesByCountry(country);
    }

    @GetMapping("/fetch-postal-codes")
    @ResponseBody
    public Set<String> fetchPostalCodesForCountryAndCity(
            @RequestParam String country,
            @RequestParam String city
    ) {
        return deliveryAreaService.findPostalCodesByCountryAndCity(country, city);
    }

    @GetMapping("/fetch-streets")
    @ResponseBody
    public Set<String> fetchStreetsForCountryAndCityAndPostalCode(
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam String postalCode
    ) {
        return deliveryAreaService.findStreetsByCountryAndCityAndPostalCode(country, city, postalCode);
    }
}
