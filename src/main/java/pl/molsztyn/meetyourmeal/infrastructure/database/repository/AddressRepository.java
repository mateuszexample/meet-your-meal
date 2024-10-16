package pl.molsztyn.meetyourmeal.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.business.dao.AddressDAO;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.jpa.AddressJpaRepository;
import pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper.AddressEntityMapper;

@Repository
@AllArgsConstructor
public class AddressRepository implements AddressDAO {

    private final AddressJpaRepository addressJpaRepository;

    private final AddressEntityMapper addressEntityMapper;

    @Override
    public void deleteById(Integer addressId) {
        addressJpaRepository.deleteById(addressId);
    }
}
