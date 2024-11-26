package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.molsztyn.meetyourmeal.domain.Address;
import pl.molsztyn.meetyourmeal.domain.Customer;
import pl.molsztyn.meetyourmeal.domain.OrderDetails;
import pl.molsztyn.meetyourmeal.domain.enums.OrderStatus;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomerDashboardService {

    private final CustomerService customerService;
    private final AddressService addressService;
    private final OrderService orderService;

    public Customer getCurrentlyLoggedInCustomer(Authentication authentication) {
        return customerService.findCustomer(authentication);
    }

    public Set<OrderDetails> getAllOrdersForCustomer(Authentication authentication) {
        Integer customerId = getCurrentlyLoggedInCustomer(authentication).getCustomerId();
        return orderService.getAllOrdersForCustomer(customerId);
    }

    public void updateOrderStatus(String orderNumber, OrderStatus orderStatus, Authentication authentication) {
        Integer customerId = getCurrentlyLoggedInCustomer(authentication).getCustomerId();
        Set<OrderDetails> allOrdersForCustomer = orderService.getAllOrdersForCustomer(customerId);

        Optional<OrderDetails> optionalOrderDetails = allOrdersForCustomer.stream()
                .filter(orderDetails -> orderNumber.equals(orderDetails.getOrderNumber()))
                .findFirst();
        if (optionalOrderDetails.isEmpty()) {
            throw new NotFoundException("Could not find order by orderNumber: [%s]".formatted(orderNumber));
        }

        OrderDetails orderToUpdate = optionalOrderDetails.get();
        if (OrderStatus.CANCELLED.equals(orderStatus)) {
            orderService.update(orderToUpdate.withOrderStatus(OrderStatus.CANCELLED_BY_CUSTOMER));
        } else {
            orderService.update(orderToUpdate.withOrderStatus(orderStatus));
        }
    }

    public OffsetDateTime getOrderToCancelTime(String orderNumber) {
        return orderService.getToCancelTime(orderNumber);
    }

    public void updateAddress(Authentication authentication, Address newAddress) {
        Customer customer = customerService.findCustomer(authentication);
        if (Objects.isNull(customer.getAddress())) {
            customerService.update(customer.withAddress(newAddress));
        } else {
            Integer oldAddressId = customer.getAddress().getAddressId();
            customerService.update(customer.withAddress(newAddress));
            addressService.deleteAddressById(oldAddressId);
        }
    }
}
