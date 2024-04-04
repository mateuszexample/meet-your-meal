package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.molsztyn.meetyourmeal.business.dao.DeliveryAreaDAO;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;
import pl.molsztyn.meetyourmeal.domain.DeliveryAreaAddRequest;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeliveryAreaService {

    private final DeliveryAreaDAO deliveryAreaDAO;

    public Optional<DeliveryArea> findDeliveryArea(DeliveryAreaAddRequest deliveryAreaAddRequest) {

        Optional<DeliveryArea> deliveryAreaOpt = deliveryAreaDAO.findByCountryAndCityAndPostalCodeAndStreet(
                deliveryAreaAddRequest.getCountry(),
                deliveryAreaAddRequest.getCity(),
                deliveryAreaAddRequest.getPostalCode(),
                deliveryAreaAddRequest.getStreet()
        );

        return deliveryAreaOpt;
    }
}
