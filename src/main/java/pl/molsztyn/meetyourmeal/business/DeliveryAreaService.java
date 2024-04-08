package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.molsztyn.meetyourmeal.business.dao.DeliveryAreaDAO;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;
import pl.molsztyn.meetyourmeal.domain.DeliveryAreaRequest;
import pl.molsztyn.meetyourmeal.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class DeliveryAreaService {

    private final DeliveryAreaDAO deliveryAreaDAO;

    public Optional<DeliveryArea> findDeliveryArea(DeliveryAreaRequest deliveryAreaRequest) {

        return deliveryAreaDAO.findByCountryAndCityAndPostalCodeAndStreet(
                deliveryAreaRequest.getCountry(),
                deliveryAreaRequest.getCity(),
                deliveryAreaRequest.getPostalCode(),
                deliveryAreaRequest.getStreet()
        );
    }

    public List<DeliveryArea> findAll() {
        return deliveryAreaDAO.findAll();
    }


    public Set<String> findCitiesByCountry(String country) {
        return deliveryAreaDAO.findCitiesByCountry(country);
    }

    public Set<String> findPostalCodesByCountryAndCity(String country, String city) {
        return deliveryAreaDAO.findPostalCodesByCountryAndCity(country, city);
    }

    public Set<String> findStreetsByCountryAndCityAndPostalCode(String country, String city, String postalCode) {
        return deliveryAreaDAO.findStreetsByCountryAndCityAndPostalCode(country, city, postalCode);
    }

    public Integer findDeliveryAreaId(DeliveryAreaRequest deliveryAreaRequest) {
        Optional<DeliveryArea> deliveryAreaOpt = findDeliveryArea(deliveryAreaRequest);
        if (deliveryAreaOpt.isEmpty()) {
            throw new NotFoundException("Could not find delivery area by provided data");
        }
        return deliveryAreaOpt.get().getDeliveryAreaId();
    }

}
