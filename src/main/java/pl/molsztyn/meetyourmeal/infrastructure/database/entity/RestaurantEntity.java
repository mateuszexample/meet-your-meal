package pl.molsztyn.meetyourmeal.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "restaurantId")
@ToString(of = {"restaurantId", "name"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "restaurant_delivery_area",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "delivery_area_id")
    )
    private Set<DeliveryAreaEntity> deliveryAreas;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "restaurant_food_category",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "food_category_id")
    )
    private Set<FoodCategoryEntity> foodCategories;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "restaurant")
    private Set<FoodEntity> menu;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private RestaurantOwnerEntity restaurantOwner;
}
