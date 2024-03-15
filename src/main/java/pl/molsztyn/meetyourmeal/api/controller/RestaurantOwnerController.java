package pl.molsztyn.meetyourmeal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RestaurantOwnerController {
    private static final String RESTAURANT_OWNER = "/restaurant-owner";

    @GetMapping(value = RESTAURANT_OWNER)
    public String homePage() {
        return "restaurant_owner";
    }

}
