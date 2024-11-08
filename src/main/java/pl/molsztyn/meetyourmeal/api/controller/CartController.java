package pl.molsztyn.meetyourmeal.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.molsztyn.meetyourmeal.business.CartService;
import pl.molsztyn.meetyourmeal.domain.Cart;
import pl.molsztyn.meetyourmeal.domain.Customer;
import pl.molsztyn.meetyourmeal.domain.enums.CartAction;

import java.util.Objects;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class CartController {
    private static final String CART_ADD_TO_CART = "/restaurant-details/{name}/cart/add-to-cart/{id}";
    private static final String CART_UPDATE_QUANTITY = "/restaurant-details/{name}/cart/update-quantity";

    private final CartService cartService;

    @PostMapping(CART_ADD_TO_CART)
    public String cartAddToCart(
            @PathVariable("name") String restaurantName,
            @PathVariable("id") Integer foodId,
            @RequestParam("selectedCategory") String selectedCategory,
            Authentication authentication
    ) {
        Customer customer = cartService.getCurrentlyLoggedInCustomer(authentication);

        Integer cartId;
        if (customer.getCarts().isEmpty() || !hasCartInThisRestaurant(customer, restaurantName)) {
            cartId = cartService.create(restaurantName, foodId, customer);
        } else {
            cartId = cartService.update(restaurantName, foodId, CartAction.ADD_OR_INCREASE, customer);
        }

        return "redirect:/restaurant-details/{name}/menu/category/" + selectedCategory + "?cartModal=true&cartId=" + cartId;
    }

    private boolean hasCartInThisRestaurant(Customer customer, String restaurantName) {
        Optional<Cart> cartOptional = customer.getCarts().stream()
                .filter(cart -> restaurantName.equals(cart.getRestaurant().getName()))
                .findFirst();
        return cartOptional.isPresent();
    }

    @PostMapping(CART_UPDATE_QUANTITY)
    public String cartUpdateQuantity(
            @PathVariable("name") String restaurantName,
            @RequestParam("selectedCategory") String selectedCategory,
            @RequestParam("foodId") Integer foodId,
            @RequestParam("action") CartAction action,
            Authentication authentication
    ) {
        Customer customer = cartService.getCurrentlyLoggedInCustomer(authentication);
        Integer cartId = cartService.update(restaurantName, foodId, action, customer);
        boolean isCartDeleted = Objects.isNull(cartId);

        return isCartDeleted
        ? "redirect:/restaurant-details/{name}/menu/category/" + selectedCategory
        : "redirect:/restaurant-details/{name}/menu/category/" + selectedCategory + "?cartModal=true&cartId=" + cartId;
    }
}
