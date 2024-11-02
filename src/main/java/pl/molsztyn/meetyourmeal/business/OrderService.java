package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.CartDAO;
import pl.molsztyn.meetyourmeal.business.dao.OrderItemDAO;
import pl.molsztyn.meetyourmeal.domain.*;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class OrderService {

    private final RestaurantService restaurantService;
    private final CustomerService customerService;

    private final CartDAO cartDAO;
    private final OrderItemDAO orderItemDAO;

    @Transactional
    public Integer createCart(String restaurantName, Integer foodId, Authentication authentication) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);
        Food food = restaurant.getMenu().stream()
                .filter(f -> foodId.equals(f.getFoodId()))
                .findFirst().orElseThrow(() -> new NotFoundException("Could not find food by id: [%s]".formatted(foodId)));

        Customer customer = getCurrentlyLoggedInCustomer(authentication);
        Cart cart = createEmptyCart(restaurant, customer);
        OrderItem orderItem = buildOrderItem(cart, food);
        orderItemDAO.createOrderItem(orderItem);
        return cart.getCartId();
    }

    private OrderItem buildOrderItem(Cart cart, Food food) {
        return OrderItem.builder()
                .quantity(1)
                .orderItemValue(food.getPrice())
                .food(food)
                .cart(cart)
                .build();
    }

    private Cart createEmptyCart(Restaurant restaurant, Customer customer) {
        Cart emptyCart = Cart.builder()
                .totalPrice(BigDecimal.ZERO)
                .customer(customer)
                .restaurant(restaurant)
                .orderItems(new HashSet<>())
                .build();

        return cartDAO.createCart(emptyCart);
    }

    public Customer getCurrentlyLoggedInCustomer(Authentication authentication) {
        return customerService.getCustomer(authentication);
    }
}
