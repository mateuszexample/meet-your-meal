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
public class CartService {

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

    public void updateCart(String restaurantName, String itemName, Integer cartId, String action, Authentication authentication) {
        Customer customer = getCurrentlyLoggedInCustomer(authentication);
        Cart cart = customer.getCarts().stream()
                .filter(c -> cartId.equals(c.getCartId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Could not find cart by id: [%s]".formatted(cartId)));

        switch (action) {
            case "increase" -> cartIncreaseQuantity(cart, itemName);
//            case "decrease" -> cartDecreaseQuantity(cart, itemName);
            default -> throw new RuntimeException("Something is not yes with action value");
        }
    }

    @Transactional
    private void cartIncreaseQuantity(Cart cart, String itemName) {
        OrderItem item = cart.getOrderItems().stream()
                .filter(orderItem -> itemName.equals(orderItem.getFood().getName()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Could not find food by item name: [%s]".formatted(itemName)));

        Integer increasedQuantity = item.getQuantity() + 1;
        BigDecimal increasedItemValue = item.getFood().getPrice().multiply(BigDecimal.valueOf(increasedQuantity));
        OrderItem updatedOrderItem = item.withQuantity(increasedQuantity).withOrderItemValue(increasedItemValue);

        cart.getOrderItems().remove(item);
        cart.getOrderItems().add(updatedOrderItem);

        BigDecimal cartTotalPrice = cart.getOrderItems().stream()
                .map(OrderItem::getOrderItemValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Cart updatedCart = cart.withTotalPrice(cartTotalPrice);

        orderItemDAO.createOrderItem(updatedOrderItem.withCart(updatedCart));
        cartDAO.updateCart(updatedCart);
    }
}