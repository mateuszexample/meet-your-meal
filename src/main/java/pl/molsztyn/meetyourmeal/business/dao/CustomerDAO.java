package pl.molsztyn.meetyourmeal.business.dao;

import pl.molsztyn.meetyourmeal.domain.Customer;

import java.util.Optional;

public interface CustomerDAO {

    Optional<Customer> findByUserId(Integer userId);

    void update(Customer customer);
}
