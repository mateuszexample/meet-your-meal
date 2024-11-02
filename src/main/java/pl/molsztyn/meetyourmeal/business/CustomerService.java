package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.molsztyn.meetyourmeal.business.dao.CustomerDAO;
import pl.molsztyn.meetyourmeal.domain.Customer;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;
import pl.molsztyn.meetyourmeal.infrastructure.security.MeetYourMealUserDetailsService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final MeetYourMealUserDetailsService meetYourMealUserDetailsService;
    private final CustomerDAO customerDAO;

    public Customer findCustomer(Integer userId) {
        Optional<Customer> customer = customerDAO.findByUserId(userId);
        if (customer.isEmpty()) {
            throw new NotFoundException("Could not find customer by user id: [%s]".formatted(userId));
        }
        return customer.get();
    }

    public Customer getCustomer(Authentication authentication) {
        String username = authentication.getName();
        Integer userId = meetYourMealUserDetailsService.getUserId(username);
        return findCustomer(userId);
    }
}
