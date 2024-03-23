package pl.molsztyn.meetyourmeal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class RestaurantOwnerController {
    private static final String OWNER = "/owner-dashboard";
    private static final String OWNER_RESTAURANT_DETAILS = "/owner-dashboard/restaurant-details";

    @RequestMapping(value = OWNER, method = RequestMethod.GET)
    public String ownerDashboard() {
        return "owner_dashboard";
    }

    @RequestMapping(value = OWNER_RESTAURANT_DETAILS, method = RequestMethod.GET)
    public String ownerRestaurantDetails() {
        return "owner_dashboard#restaurant_details";
    }

}
