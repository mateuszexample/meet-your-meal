package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.CartDAO;
import pl.molsztyn.meetyourmeal.business.dao.OrderItemDAO;
import pl.molsztyn.meetyourmeal.domain.*;
import pl.molsztyn.meetyourmeal.domain.enums.CartAction;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    private final RestaurantService restaurantService;
    private final CustomerService customerService;

    private final CartDAO cartDAO;
    private final OrderItemDAO orderItemDAO;

    @Transactional
    public Integer create(String restaurantName, Integer foodId, Customer customer) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);

        Food food = findFoodInRestaurant(restaurant, foodId);
        Cart cart = createEmptyCart(restaurant, customer);
        Integer cartId = cart.getCartId();
        OrderItem orderItem = buildOrderItem(cartId, food);
        cart.getOrderItems().add(orderItem);

        orderItemDAO.createOrderItem(orderItem);
        cartCalculateTotalPriceAndUpdate(cart);

        return cartId;
    }

    @Transactional
    public Integer update(String restaurantName, Integer foodId, CartAction action, Customer customer) {
        Cart cart = customer.getCarts().stream()
                .filter(c -> restaurantName.equals(c.getRestaurant().getName()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Something is not yes, could not find the cart"));

        Optional<OrderItem> optionalOrderItem = cart.getOrderItems().stream()
                .filter(orderItem -> foodId.equals(orderItem.getFood().getFoodId()))
                .findFirst();

        if (CartAction.ADD_OR_INCREASE.equals(action) && optionalOrderItem.isEmpty()) {
           return cartAddNewItem(restaurantName, foodId, cart);
        } else if (CartAction.ADD_OR_INCREASE.equals(action)) {
            action = CartAction.INCREASE;
        }

        OrderItem item = optionalOrderItem.orElseThrow(() -> new NotFoundException("Could not find the item in cart"));

        return switch (action) {
            case INCREASE -> cartUpdateQuantity(cart, item, 1);
            case DECREASE -> cartUpdateQuantity(cart, item, -1);
            case REMOVE -> cartRemoveItem(cart, item);
            default -> throw new RuntimeException("Something is not yes with action value");
        };
    }

    @Transactional
    public void delete(Cart cart) {
        cartDAO.deleteCartById(cart.getCartId());
    }

    private Integer cartUpdateQuantity(Cart cart, OrderItem item, int value) {
        int updatedQuantity = item.getQuantity() + value;
        BigDecimal updatedItemValue = item.getFood().getPrice().multiply(BigDecimal.valueOf(updatedQuantity));
        OrderItem updatedOrderItem = item.withQuantity(updatedQuantity).withOrderItemValue(updatedItemValue);

        cart.getOrderItems().remove(item);
        cart.getOrderItems().add(updatedOrderItem);

        orderItemDAO.createOrderItem(updatedOrderItem);
        cartCalculateTotalPriceAndUpdate(cart);

        return cart.getCartId();
    }

    private Food findFoodInRestaurant(Restaurant restaurant, Integer foodId) {
        return restaurant.getMenu().stream()
                .filter(f -> foodId.equals(f.getFoodId()))
                .findFirst().orElseThrow(() -> new NotFoundException("Could not find food by id: [%s]".formatted(foodId)));
    }

    private OrderItem buildOrderItem(Integer cartId, Food food) {
        return OrderItem.builder()
                .quantity(1)
                .orderItemValue(food.getPrice())
                .food(food)
                .cart(Cart.builder().cartId(cartId).build())
                .build();
    }

    private Cart createEmptyCart(Restaurant restaurant, Customer customer) {
        Cart emptyCart = Cart.builder()
                .totalPrice(BigDecimal.ZERO)
                .customer(customer)
                .restaurant(restaurant)
                .orderItems(new ArrayList<>())
                .build();

        return cartDAO.createCart(emptyCart);
    }

    public Customer getCurrentlyLoggedInCustomer(Authentication authentication) {
        return customerService.getCustomer(authentication);
    }

    private Integer cartRemoveItem(Cart cart, OrderItem item) {
        cart.getOrderItems().remove(item);

        if (!cart.getOrderItems().isEmpty()) {
            cartCalculateTotalPriceAndUpdate(cart);
            orderItemDAO.deleteOrderItemById(item.getOrderItemId());
            return cart.getCartId();
        } else {
            orderItemDAO.deleteOrderItemById(item.getOrderItemId());
            delete(cart);
            return null;
        }
    }

    private void cartCalculateTotalPriceAndUpdate(Cart cart) {
        BigDecimal cartTotalPrice = cart.getOrderItems().stream()
                .map(OrderItem::getOrderItemValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Cart updatedCart = cart.withTotalPrice(cartTotalPrice);

        cartDAO.updateCart(updatedCart);
    }


    private Integer cartAddNewItem(String restaurantName, Integer foodId, Cart cart) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);
        Food food = findFoodInRestaurant(restaurant, foodId);
        OrderItem item = buildOrderItem(cart.getCartId(), food);
        cart.getOrderItems().add(item);
        orderItemDAO.createOrderItem(item);
        cartCalculateTotalPriceAndUpdate(cart);
        return cart.getCartId();
    }

    public Cart getCartById(Integer cartId) {
        return cartDAO.findCartById(cartId)
                .orElseThrow(() -> new NotFoundException("Could not find cart by id: [%s]".formatted(cartId)));
    }
}