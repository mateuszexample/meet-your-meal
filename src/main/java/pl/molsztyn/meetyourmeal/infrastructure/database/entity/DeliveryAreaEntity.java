package pl.molsztyn.meetyourmeal.infrastructure.database.entity;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@EqualsAndHashCode(of = {"country", "city", "postalCode", "street"})
@ToString(of = {"deliveryAreaId", "country", "city", "postalCode", "street"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_area")
public class DeliveryAreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_area_id")
    private Integer deliveryAreaId;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "street")
    private String street;
}
