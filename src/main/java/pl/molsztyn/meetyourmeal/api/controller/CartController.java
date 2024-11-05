package pl.molsztyn.meetyourmeal.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.molsztyn.meetyourmeal.api.dto.mapper.OrderMapper;
import pl.molsztyn.meetyourmeal.business.CartService;
import pl.molsztyn.meetyourmeal.domain.Cart;
import pl.molsztyn.meetyourmeal.domain.Customer;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class CartController {
    private static final String CART_ADD_TO_CART = "/restaurant-details/{name}/cart/add-to-cart/{id}";
    private static final String CART_UPDATE_QUANTITY = "/restaurant-details/{name}/cart/update-quantity";

    private final CartService cartService;

    private final OrderMapper orderMapper;

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
            cartId = cartService.createCart(restaurantName, foodId, customer);
            return "redirect:/restaurant-details/{name}/menu/category/" + selectedCategory + "?cartModal=true&cartId=" + cartId;

        } else {
            Cart cart = customer.getCarts().stream()
                    .filter(c -> restaurantName.equals(c.getRestaurant().getName()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Something is not yes, could not find the cart"));
            cartService.updateCart(restaurantName, foodId, cart);
            cartId = cart.getCartId();
        }

        System.out.println("addToCart");
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
            @RequestParam("itemName") String itemName,
            @RequestParam("cartId") Integer cartId,
            @RequestParam("action") String action,
            Authentication authentication
    ) {
        cartService.updateCart(itemName, cartId, action, authentication);
        return "redirect:/restaurant-details/{name}/menu/category/" + selectedCategory + "?cartModal=true&cartId=" + cartId;
    }
}
