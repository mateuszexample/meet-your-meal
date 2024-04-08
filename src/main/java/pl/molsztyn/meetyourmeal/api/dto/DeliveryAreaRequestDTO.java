package pl.molsztyn.meetyourmeal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAreaRequestDTO {

    private String country;
    private String city;
    private String postalCode;
    private String street;
}
