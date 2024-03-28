package pl.molsztyn.meetyourmeal.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAreaDTO {

    String country;
    String city;
    String postalCode;
    String street;
}
