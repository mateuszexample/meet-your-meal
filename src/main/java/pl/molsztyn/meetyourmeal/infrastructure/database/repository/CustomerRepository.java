package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.CustomerDAO;
import pl.molsztyn.meetyourmeal.domain.Customer;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.CustomerJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.CustomerEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CustomerRepository implements CustomerDAO {

    private final CustomerJpaRepository customerJpaRepository;

    private final CustomerEntityMapper customerEntityMapper;

    @Override
    public Optional<Customer> findByUserId(Integer userId) {
        return customerJpaRepository.findByUserId(userId)
                .map(customerEntityMapper::mapFromEntity);
    }
}
