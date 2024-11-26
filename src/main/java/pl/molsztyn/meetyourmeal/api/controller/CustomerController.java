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
import pl.molsztyn.meetyourmeal.api.dto.mapper.CustomerMapper;
import pl.molsztyn.meetyourmeal.api.dto.mapper.OrderMapper;
import pl.molsztyn.meetyourmeal.business.CustomerDashboardService;
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
    private static final String CUSTOMER_ORDERS = "/customer-dashboard/orders";
    private static final String CUSTOMER_ORDERS_HISTORY = "/customer-dashboard/orders-history";
    private static final String CUSTOMER_ORDER_UPDATE_STATUS = "/customer-dashboard/order/update-status";
    private static final String CUSTOMER_DELETE_ACCOUNT = "/customer-dashboard/delete-customer";

    private final CustomerDashboardService customerDashboardService;

    private final CustomerMapper customerMapper;
    private final OrderMapper orderMapper;

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


//    @DeleteMapping(CUSTOMER_DELETE_RESTAURANT)
//    public String customerDeleteCustomer(
//            Authentication authentication
//    ) {
//
//
//            customerDashboardService.delete(authentication);
//
//
//        return "redirect:/customer-dashboard";
//    }
//

//
//    private String showCustomerDetails(Authentication authentication, Model model) {
//        Customer customer = customerDashboardService.getCustomer(authentication);
//        CustomerDTO customerDTO = customerMapper.mapToDTO(customer);
//
//        model.addAttribute("customerDTO", customerDTO);
//        model.addAttribute("customerCreateRequestDTO", new CustomerCreateRequestDTO());
//
//        if (customerDTO.isHasImage()) {
//            // gpt idea
//            String base64Image = Base64.getEncoder().encodeToString(customerDTO.getImageData());
//            model.addAttribute("base64Image", base64Image);
//        }
//
//        return "customer_dashboard#customer_details";
//    }
//
//    private String showCustomerForm(Model model) {
//        model.addAttribute("customerCreateRequestDTO", new CustomerCreateRequestDTO());
//        return "customer_dashboard#customer_details_add_form";
//    }
//
//    @PostMapping(CUSTOMER_RESTAURANT_DETAILS_ADD)
//    public String createCustomer(
//            @ModelAttribute("customerCreateRequestDTO") CustomerCreateRequestDTO customerCreateRequestDTO,
//            @RequestParam("image") MultipartFile image,
//            Authentication authentication
//    ) throws IOException {
//        CustomerCreateRequest customerCreateRequest = customerMapper.mapFromDTO(customerCreateRequestDTO);
//
//        // image upload
//        if (!image.isEmpty()) {
//            byte[] imageData = image.getBytes();
//            customerDashboardService.create(customerCreateRequest.withImageData(imageData), authentication);
//        } else {
//            customerDashboardService.create(customerCreateRequest, authentication);
//        }
//
//        return "redirect:/customer-dashboard";
//    }
//
//    @PostMapping(CUSTOMER_RESTAURANT_DETAILS_UPDATE_NAME)
//    public String updateCustomerName(
//            @ModelAttribute("customerCreateRequestDTO") CustomerCreateRequestDTO customerCreateRequestDTO,
//            Authentication authentication
//    ) {
//        String newCustomerName = customerCreateRequestDTO.getName();
//        CustomerOwner customerOwner = customerDashboardService.getCurrentlyLoggedInCustomerOwner(authentication);
//        Customer customerWithNewName = customerOwner.getCustomer().withName(newCustomerName);
//        CustomerOwner customerWithCustomerWithNewName = customerOwner.withCustomer(customerWithNewName);
//
//        customerDashboardService.update(customerWithCustomerWithNewName);
//
//        return "redirect:/customer-dashboard/customer-details";
//    }
//
//    @PostMapping(CUSTOMER_RESTAURANT_DETAILS_UPDATE_ADDRESS)
//    public String updateCustomerAddress(
//            @ModelAttribute("customerCreateRequestDTO") CustomerCreateRequestDTO customerCreateRequestDTO,
//            Authentication authentication
//    ) {
//        CustomerOwner customerOwner = customerDashboardService.getCurrentlyLoggedInCustomerOwner(authentication);
//        Integer addressId = customerOwner.getCustomer().getAddress().getAddressId();
//        Customer customerWithNewAddress = customerOwner.getCustomer().withAddress(Address.builder()
//                .country(customerCreateRequestDTO.getCustomerCountry())
//                .city(customerCreateRequestDTO.getCustomerCity())
//                .postalCode(customerCreateRequestDTO.getCustomerPostalCode())
//                .street(customerCreateRequestDTO.getCustomerStreet())
//                .buildingNumber(customerCreateRequestDTO.getCustomerBuildingNumber())
//                .unitNumber(customerCreateRequestDTO.getCustomerUnitNumber())
//                .build());
//        CustomerOwner customerWithCustomerWithNewAddress = customerOwner.withCustomer(customerWithNewAddress);
//
//        customerDashboardService.update(customerWithCustomerWithNewAddress);
//        customerDashboardService.deleteOldAddress(addressId);
//
//        return "redirect:/customer-dashboard/customer-details";
//    }
//
//    @PostMapping(CUSTOMER_RESTAURANT_DETAILS_UPDATE_IMAGE)
//    public String updateCustomerImage(
//            @RequestParam("image") MultipartFile image,
//            Authentication authentication
//    ) throws IOException {
//        CustomerOwner customerOwner = customerDashboardService.getCurrentlyLoggedInCustomerOwner(authentication);
//
//        if (!image.isEmpty()) {
//            byte[] imageData = image.getBytes();
//            Customer customerWithNewImage = customerOwner.getCustomer().withImageData(imageData);
//            customerDashboardService.update(customerOwner.withCustomer(customerWithNewImage));
//        } else {
//            Customer customerWithNoImage = customerOwner.getCustomer().withImageData(null);
//            customerDashboardService.update(customerOwner.withCustomer(customerWithNoImage));
//        }
//
//        return "redirect:/customer-dashboard/customer-details";
//    }
//

}
