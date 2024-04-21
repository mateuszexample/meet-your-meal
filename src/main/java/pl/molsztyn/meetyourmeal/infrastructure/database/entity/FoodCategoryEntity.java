package pl.molsztyn.meetyourmeal.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "category")
@ToString(of = {"foodCategoryId", "category"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_category")
public class FoodCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_category_id")
    private Integer foodCategoryId;

    @Column(name = "category")
    private String category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "foodCategory")
    private Set<FoodEntity> foods;
}
