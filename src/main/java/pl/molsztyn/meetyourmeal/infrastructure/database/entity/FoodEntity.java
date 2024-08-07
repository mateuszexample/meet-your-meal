package pl.molsztyn.meetyourmeal.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "foodId")
@ToString(of = {"foodId", "name", "price", "description", "foodCategory"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food")
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Integer foodId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "image_url")
    private String image_url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_category_id")
    private FoodCategoryEntity foodCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;
}
