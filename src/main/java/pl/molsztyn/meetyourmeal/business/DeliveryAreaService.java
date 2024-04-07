package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.molsztyn.meetyourmeal.business.dao.DeliveryAreaDAO;
import pl.molsztyn.meetyourmeal.domain.DeliveryArea;
import pl.molsztyn.meetyourmeal.domain.DeliveryAreaAddRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class DeliveryAreaService {

    private final DeliveryAreaDAO deliveryAreaDAO;

    public Optional<DeliveryArea> findDeliveryArea(DeliveryAreaAddRequest deliveryAreaAddRequest) {

        return deliveryAreaDAO.findByCountryAndCityAndPostalCodeAndStreet(
                deliveryAreaAddRequest.getCountry(),
                deliveryAreaAddRequest.getCity(),
                deliveryAreaAddRequest.getPostalCode(),
                deliveryAreaAddRequest.getStreet()
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
}
