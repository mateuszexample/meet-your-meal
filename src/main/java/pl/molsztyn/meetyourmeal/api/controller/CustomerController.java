package pl.molsztyn.meetyourmeal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.molsztyn.meetyourmeal.api.dto.AddressDTO;
import pl.molsztyn.meetyourmeal.api.dto.CustomerDTO;
import pl.molsztyn.meetyourmeal.api.dto.OrderDetailsDTO;
import pl.molsztyn.meetyourmeal.api.dto.mapper.AddressMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.CustomerMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.OrderMapper;
import pl.molsztyn.meetyourmeal.business.CustomerDashboardService;
import pl.molsztyn.meetyourmeal.domain.Address;
import pl.molsztyn.meetyourmeal.domain.Customer;
import pl.molsztyn.meetyourmeal.domain.OrderDetails;
import pl.molsztyn.meetyourmeal.domain.enums.OrderStatus;
import pl.molsztyn.meetyourmeal.domain.exception.OrderCancellationException;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private static final String CUSTOMER = "/customer-dashboard";
    private static final String CUSTOMER_DETAILS = "/customer-dashboard/customer-details";
    private static final String CUSTOMER_DETAILS_UPDATE_ADDRESS = "/customer-dashboard/customer-details/update-address";
    private static final String CUSTOMER_DETAILS_UPDATE_NAME = "/customer-dashboard/customer-details/update-name";
    private static final String CUSTOMER_DETAILS_UPDATE_PHONE = "/customer-dashboard/customer-details/update-phone";
    private static final String CUSTOMER_ORDERS = "/customer-dashboard/orders";
    private static final String CUSTOMER_ORDERS_HISTORY = "/customer-dashboard/orders-history";
    private static final String CUSTOMER_ORDER_UPDATE_STATUS = "/customer-dashboard/order/update-status";
    private static final String CUSTOMER_DELETE_ACCOUNT = "/customer-dashboard/delete-customer";

    private final CustomerDashboardService customerDashboardService;

    private final CustomerMapper customerMapper;
    private final OrderMapper orderMapper;
    private final AddressMapper addressMapper;

    @GetMapping(CUSTOMER)
    public String customerDashboard() {
        return "customer_dashboard#welcome";
    }

    @GetMapping(CUSTOMER_DETAILS)
    public String customerCustomerDetailsPage(Authentication authentication, Model model) {
        Customer customer = customerDashboardService.getCurrentlyLoggedInCustomer(authentication);
        CustomerDTO customerDTO = customerMapper.mapToDTO(customer);

        model.addAttribute("customerDTO", customerDTO);
        model.addAttribute("addressDTO", new AddressDTO());

        return "customer_dashboard#customer_details";
    }

    @PostMapping(CUSTOMER_DETAILS_UPDATE_ADDRESS)
    public String customerUpdateAddress(
            @ModelAttribute("addressDTO") AddressDTO addressDTO,
            Authentication authentication
    ) {
        Address address = addressMapper.mapFromDTO(addressDTO);
        customerDashboardService.updateAddress(authentication, address);

        return "redirect:/customer-dashboard/customer-details";
    }

    @GetMapping(CUSTOMER_ORDERS)
    public String customerOrdersPage(
            Authentication authentication,
            Model model,
            @ModelAttribute(value = "cancellationExpired") String cancellationExpired
    ) {
        Set<OrderDetails> allOrdersForCustomer = customerDashboardService.getAllOrdersForCustomer(authentication);
        Set<OrderDetailsDTO> orderDetailsDTOs = allOrdersForCustomer.stream()
                .filter(orderDetails -> OrderStatus.PENDING.equals(orderDetails.getOrderStatus()))
                .map(orderMapper::mapToDTO)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(OrderDetailsDTO::getReceivedTime))));

        model.addAttribute("orderDetailsDTOs", orderDetailsDTOs);

        boolean showCancellationExpiredModal = "true".equalsIgnoreCase(cancellationExpired);
        model.addAttribute("cancellationExpiredModal", showCancellationExpiredModal);

        return "customer_dashboard#orders";
    }

    @GetMapping(CUSTOMER_ORDERS_HISTORY)
    public String customerOrdersHistoryPage(
            Authentication authentication,
            Model model
    ) {
        Set<OrderDetails> allOrdersForCustomer = customerDashboardService.getAllOrdersForCustomer(authentication);
        Set<OrderDetailsDTO> orderDetailsDTOs = allOrdersForCustomer.stream()
                .filter(orderDetails -> !OrderStatus.PENDING.equals(orderDetails.getOrderStatus()))
                .map(orderMapper::mapToDTO)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(OrderDetailsDTO::getReceivedTime))));

        orderDetailsDTOs.stream()
                .filter(orderDetailsDTO -> OrderStatus.CANCELLED_BY_CUSTOMER.equals(orderDetailsDTO.getOrderStatus()))
                .forEach(orderDetailsDTO -> orderDetailsDTO.setOrderStatus(OrderStatus.CANCELLED));

        model.addAttribute("orderDetailsDTOs", orderDetailsDTOs);

        return "customer_dashboard#orders_history";
    }

    @PostMapping(CUSTOMER_ORDER_UPDATE_STATUS)
    public String customerOrderUpdateStatus(
            @RequestParam("orderNumber") String orderNumber,
            @RequestParam("orderStatus") OrderStatus orderStatus,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime toCancelTime = customerDashboardService.getOrderToCancelTime(orderNumber);

        boolean isTimeToCancelPassed = now.isAfter(toCancelTime);
        boolean isCorrectStatus = OrderStatus.CANCEL.equals(orderStatus);

        if (isCorrectStatus) {
            if (!isTimeToCancelPassed) {
                customerDashboardService.updateOrderStatus(orderNumber, OrderStatus.CANCELLED, authentication);
            } else {
                redirectAttributes.addFlashAttribute("cancellationExpired", true);
            }
        } else {
            throw new OrderCancellationException("Something is not yes with Order Status");
        }

        return "redirect:/customer-dashboard/orders";
    }
}
