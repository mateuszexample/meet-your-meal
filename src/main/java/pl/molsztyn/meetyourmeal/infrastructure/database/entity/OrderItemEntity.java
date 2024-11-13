package pl.molsztyn.meetyourmeal.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "orderItemId")
@ToString(of = {"orderItemId", "foodName", "quantity", "orderItemValue", "orderDetails"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "order_item_value")
    private BigDecimal orderItemValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_details_id")
    private OrderDetailsEntity orderDetails;
}
