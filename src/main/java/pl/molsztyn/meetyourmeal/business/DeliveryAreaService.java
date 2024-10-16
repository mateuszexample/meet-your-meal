package pl.molsztyn.meetyourmeal.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public Optional<DeliveryArea> findDeliveryArea(DeliveryAreaRequest deliveryAreaRequest) {

        return deliveryAreaDAO.findByCountryAndCityAndPostalCodeAndStreet(
                deliveryAreaRequest.getCountry(),
                deliveryAreaRequest.getCity(),
                deliveryAreaRequest.getPostalCode(),
                deliveryAreaRequest.getStreet()
        );
    }

    @Transactional
    public List<DeliveryArea> findAll() {
        return deliveryAreaDAO.findAll();
    }

    @Transactional
    public Set<String> findCitiesByCountry(String country) {
        return deliveryAreaDAO.findCitiesByCountry(country);
    }

    @Transactional
    public Set<String> findPostalCodesByCountryAndCity(String country, String city) {
        return deliveryAreaDAO.findPostalCodesByCountryAndCity(country, city);
    }

    @Transactional
    public Set<String> findStreetsByCountryAndCityAndPostalCode(String country, String city, String postalCode) {
        return deliveryAreaDAO.findStreetsByCountryAndCityAndPostalCode(country, city, postalCode);
    }

    @Transactional
    public Integer findDeliveryAreaId(DeliveryAreaRequest deliveryAreaRequest) {
        Optional<DeliveryArea> deliveryAreaOpt = findDeliveryArea(deliveryAreaRequest);
        if (deliveryAreaOpt.isEmpty()) {
            throw new NotFoundException("Could not find delivery area by provided data");
        }
        return deliveryAreaOpt.get().getDeliveryAreaId();
    }

    @Transactional
    public List<DeliveryArea> findUnusedDeliveryAreas() {
        return deliveryAreaDAO.findByRestaurantsIsEmpty();
    }

    @Transactional
    public void deleteAll(List<DeliveryArea> unusedDeliveryAreas) {
        deliveryAreaDAO.deleteAll(unusedDeliveryAreas);
    }
}
