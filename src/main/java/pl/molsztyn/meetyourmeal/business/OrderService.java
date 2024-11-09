package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.OrderDetailsDAO;
import pl.molsztyn.meetyourmeal.business.dao.OrderItemDAO;
import pl.molsztyn.meetyourmeal.domain.Cart;
import pl.molsztyn.meetyourmeal.domain.Customer;
import pl.molsztyn.meetyourmeal.domain.OrderDetails;
import pl.molsztyn.meetyourmeal.domain.OrderItem;
import pl.molsztyn.meetyourmeal.domain.enums.OrderStatus;
import pl.molsztyn.meetyourmeal.domain.exception.ProcessingException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {

    private final CustomerService customerService;
    private final CartService cartService;

    private final OrderDetailsDAO orderDetailsDAO;
    private final OrderItemDAO orderItemDAO;

    @Transactional
    public OrderDetails create(Cart cart, Authentication authentication) {
        validateCustomer(cart, authentication);
        OrderDetails orderDetails = buildOrder(cart);
        OrderDetails orderDetailsSaved = orderDetailsDAO.create(orderDetails);

        List<OrderItem> orderItems = orderDetailsSaved.getOrderItems().stream()
                .map(orderItem ->
                        orderItem.withOrderDetails(OrderDetails.builder()
                                .orderDetailsId(orderDetailsSaved.getOrderDetailsId())
                                .build()))
                .toList();

        orderItems.forEach(orderItemDAO::createOrderItem);

        cartService.delete(cart);
        return orderDetailsSaved.withOrderItems(orderItems);
    }

    private OrderDetails buildOrder(Cart cart) {
        OffsetDateTime receivedTime = OffsetDateTime.now();
        OffsetDateTime toCancelTime = receivedTime.plusMinutes(20);

        List<OrderItem> orderItems = cart.getOrderItems().stream().map(orderItem -> orderItem.withCart(null)).toList();

        return OrderDetails.builder()
                .orderNumber(UUID.randomUUID().toString())
                .receivedTime(receivedTime)
                .toCancelTime(toCancelTime)
                .orderValue(cart.getTotalPrice())
                .orderStatus(OrderStatus.PENDING)
                .customer(cart.getCustomer())
                .restaurant(cart.getRestaurant())
                .orderItems(orderItems)
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
        return customerService.getCustomer(authentication);
    }

}