package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.OrderDetailsDAO;
import pl.molsztyn.meetyourmeal.domain.*;
import pl.molsztyn.meetyourmeal.domain.enums.OrderStatus;
import pl.molsztyn.meetyourmeal.domain.exception.ProcessingException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    public static final DateTimeFormatter FORMATTER_DB = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssxxx");
    public static final DateTimeFormatter FORMATTER_VIEW = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    private final CustomerService customerService;
    private final CartService cartService;

    private final OrderDetailsDAO orderDetailsDAO;

    @Transactional
    public OrderDetails create(Cart cart, Authentication authentication) {
        validateCustomer(cart, authentication);

        OrderDetails orderDetails = buildOrder(cart);
        OrderDetails orderDetailsSaved = orderDetailsDAO.create(orderDetails);

        List<CartItem> cartItems = cart.getCartItems();
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> buildOrderItem(cartItem, orderDetailsSaved))
                .collect(Collectors.toCollection(ArrayList::new));

        update(orderDetailsSaved.withOrderItems(orderItems));

        cartService.delete(cart);

        return orderDetailsSaved;
    }

    @Transactional
    public void update(OrderDetails orderDetails) {
        orderDetailsDAO.create(orderDetails);
    }

    private OrderDetails buildOrder(Cart cart) {
        OffsetDateTime now = OffsetDateTime.now();

        OffsetDateTime receivedTime = OffsetDateTime.parse(now.format(FORMATTER_DB));
        OffsetDateTime toCancelTime = receivedTime.plusMinutes(20);

        return OrderDetails.builder()
                .orderNumber(UUID.randomUUID().toString().substring(0, 8))
                .receivedTime(receivedTime)
                .toCancelTime(toCancelTime)
                .orderValue(cart.getTotalPrice())
                .orderStatus(OrderStatus.PENDING)
                .customer(cart.getCustomer())
                .restaurant(cart.getRestaurant())
                .orderItems(new ArrayList<>())
                .build();
    }

    private OrderItem buildOrderItem(CartItem cartItem, OrderDetails orderDetailsSaved) {
        return OrderItem.builder()
                .foodName(cartItem.getFood().getName())
                .quantity(cartItem.getQuantity())
                .orderItemValue(cartItem.getCartItemValue())
                .orderDetails(orderDetailsSaved)
                .build();
    }

    private void validateCustomer(Cart cart, Authentication authentication) {
        Customer customerAuthentication = getCurrentlyLoggedInCustomer(authentication);
        Customer customerCart = cart.getCustomer();

        if (!customerAuthentication.equals(customerCart)) {
            throw new ProcessingException("Who are you?");
        }
    }

    private Customer getCurrentlyLoggedInCustomer(Authentication authentication) {
        return customerService.findCustomer(authentication);
    }

    public Set<OrderDetails> getAllOrdersForRestaurant(Integer restaurantId) {
        return orderDetailsDAO.findAllByRestaurantId(restaurantId);
    }

    public Set<OrderDetails> getAllOrdersForCustomer(Integer customerId) {
        return orderDetailsDAO.findAllByCustomerId(customerId);
    }

    public OffsetDateTime getToCancelTime(String orderNumber) {
        return orderDetailsDAO.findToCancelTime(orderNumber);
    }

    public boolean hasCustomerProvidedAddress(Authentication authentication) {
        Customer customer = getCurrentlyLoggedInCustomer(authentication);
        return Objects.nonNull(customer.getAddress());
    }
}