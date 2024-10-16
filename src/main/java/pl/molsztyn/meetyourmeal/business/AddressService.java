package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.molsztyn.meetyourmeal.business.dao.AddressDAO;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressDAO addressDAO;

    @Transactional
    public void deleteAddressById(Integer addressId) {
        addressDAO.deleteById(addressId);
    }
}
