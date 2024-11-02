package pl.molsztyn.meetyourmeal.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.molsztyn.meetyourmeal.api.dto.mapper.OrderMapper;
import pl.molsztyn.meetyourmeal.business.OrderService;

@Controller
@AllArgsConstructor
public class OrderController {
    private static final String CART_ADD_TO_CART = "/restaurant-details/{name}/cart/add-to-cart/{id}";
    private static final String CART_VIEW = "/restaurant-details/{name}/cart/view";

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @PostMapping(CART_ADD_TO_CART)
    public String cartAddToCart(
            @PathVariable("name") String restaurantName,
            @PathVariable("id") Integer foodId,
            @RequestParam("selectedCategory") String selectedCategory,
            Authentication authentication
    ) {
        Integer cartId = orderService.createCart(restaurantName, foodId, authentication);

        System.out.println("addToCart");
        return "redirect:/restaurant-details/{name}/menu/category/" + selectedCategory + "?cartModal=true&cartId=" + cartId;
//        return "redirect:/restaurant-details/{name}/cart/view?cartId=" + cartId;
    }

//    @GetMapping(CART_VIEW)
//    public String cartShowCartView(
//            @RequestParam("cartId") Integer cartId,
//            Model model,
//            Authentication authentication
//    ) {
//        Customer customer = orderService.getCurrentlyLoggedInCustomer(authentication);
//        Cart cart = customer.getCarts().stream()
//                .filter(c -> cartId.equals(c.getCartId()))
//                .findFirst()
//                .orElseThrow(() -> new NotFoundException("Could not find cart by id: [%s]".formatted(cartId)));
//
//        Set<OrderItemDTO> cartItems = cart.getOrderItems().stream()
//                .map(orderMapper::mapToDTO)
//                .collect(Collectors.toSet());
//
//        model.addAttribute("cartItems", cartItems);
//        model.addAttribute("cartId", cartId);
//        model.addAttribute("restaurantName", cart.getRestaurant().getName());
//        model.addAttribute("restaurantCategory", "All");
//
//        return "restaurant_details#cart";
//    }
}
