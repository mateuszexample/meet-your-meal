package pl.molsztyn.meetyourmeal.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.molsztyn.meetyourmeal.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "orderDetailsId")
@ToString(of = {"orderDetailsId", "orderNumber", "receivedTime", "toCancelTime", "orderValue", "orderStatus"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_details_id")
    private Integer orderDetailsId;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "received_time")
    private OffsetDateTime receivedTime;

    @Column(name = "to_cancel_time")
    private OffsetDateTime toCancelTime;

    @Column(name = "order_value")
    private BigDecimal orderValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orderDetails")
    private List<OrderItemEntity> orderItems;
}
