package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.CartDAO;
import pl.molsztyn.meetyourmeal.business.dao.CartItemDAO;
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
    private final CartItemDAO cartItemDAO;

    @Transactional
    public Integer create(String restaurantName, Integer foodId, Customer customer) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);

        Food food = findFoodInRestaurant(restaurant, foodId);
        Cart cart = createEmptyCart(restaurant, customer);
        CartItem cartItem = buildCartItem(cart, food);
        cart.getCartItems().add(cartItem);

        return cartCalculateTotalPriceUpdateAndReturnCartId(cart);
    }

    @Transactional
    public Integer update(String restaurantName, Integer foodId, CartAction action, Customer customer) {
        Cart cart = customer.getCarts().stream()
                .filter(c -> restaurantName.equals(c.getRestaurant().getName()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Something is not yes, could not find the cart"));

        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(cartItem -> foodId.equals(cartItem.getFood().getFoodId()))
                .findFirst();

        if (CartAction.ADD_OR_INCREASE.equals(action) && optionalCartItem.isEmpty()) {
            return cartAddNewItem(restaurantName, foodId, cart);
        } else if (CartAction.ADD_OR_INCREASE.equals(action)) {
            action = CartAction.INCREASE;
        }

        CartItem item = optionalCartItem.orElseThrow(() -> new NotFoundException("Could not find the item in cart"));

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

    private Integer cartUpdateQuantity(Cart cart, CartItem item, int value) {
        int updatedQuantity = item.getQuantity() + value;
        BigDecimal updatedItemValue = item.getFood().getPrice().multiply(BigDecimal.valueOf(updatedQuantity));
        CartItem updatedCartItem = item.withQuantity(updatedQuantity).withCartItemValue(updatedItemValue);

        cart.getCartItems().remove(item);
        cart.getCartItems().add(updatedCartItem);

        return cartCalculateTotalPriceUpdateAndReturnCartId(cart);
    }

    private Food findFoodInRestaurant(Restaurant restaurant, Integer foodId) {
        return restaurant.getMenu().stream()
                .filter(f -> foodId.equals(f.getFoodId()))
                .findFirst().orElseThrow(() -> new NotFoundException("Could not find food by id: [%s]".formatted(foodId)));
    }

    private CartItem buildCartItem(Cart cart, Food food) {
        return CartItem.builder()
                .quantity(1)
                .cartItemValue(food.getPrice())
                .food(food)
                .cart(cart)
                .build();
    }

    private Cart createEmptyCart(Restaurant restaurant, Customer customer) {
        Cart emptyCart = Cart.builder()
                .totalPrice(BigDecimal.ZERO)
                .customer(customer)
                .restaurant(restaurant)
                .cartItems(new ArrayList<>())
                .build();

        return cartDAO.createCart(emptyCart);
    }

    public Customer getCurrentlyLoggedInCustomer(Authentication authentication) {
        return customerService.getCustomer(authentication);
    }

    private Integer cartRemoveItem(Cart cart, CartItem item) {
        cart.getCartItems().remove(item);

        if (!cart.getCartItems().isEmpty()) {
            cartItemDAO.deleteCartItemById(item.getCartItemId());
            return cartCalculateTotalPriceUpdateAndReturnCartId(cart);
        } else {
            delete(cart);
            return null;
        }
    }

    private Integer cartCalculateTotalPriceUpdateAndReturnCartId(Cart cart) {
        BigDecimal cartTotalPrice = cart.getCartItems().stream()
                .map(CartItem::getCartItemValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Cart updatedCart = cart.withTotalPrice(cartTotalPrice);

        cartDAO.updateCart(updatedCart);

        return cart.getCartId();
    }


    private Integer cartAddNewItem(String restaurantName, Integer foodId, Cart cart) {
        Restaurant restaurant = restaurantService.getRestaurantByName(restaurantName);
        Food food = findFoodInRestaurant(restaurant, foodId);
        CartItem item = buildCartItem(cart, food);
        cart.getCartItems().add(item);
        return cartCalculateTotalPriceUpdateAndReturnCartId(cart);
    }

    public Cart getCartById(Integer cartId) {
        return cartDAO.findCartById(cartId)
                .orElseThrow(() -> new NotFoundException("Could not find cart by id: [%s]".formatted(cartId)));
    }
}