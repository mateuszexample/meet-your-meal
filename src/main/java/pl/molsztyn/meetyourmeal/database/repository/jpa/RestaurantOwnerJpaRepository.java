package pl.molsztyn.meetyourmeal.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.molsztyn.meetyourmeal.database.entity.RestaurantOwnerEntity;

import java.util.Optional;

@Repository
public interface RestaurantOwnerJpaRepository extends JpaRepository<RestaurantOwnerEntity, Integer> {

    Optional<RestaurantOwnerEntity> findByPesel(String pesel);
}
