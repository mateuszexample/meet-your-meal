package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "customerId")
@ToString(of = {"customerId", "name", "surname", "phone"})
public class Customer {

    Integer customerId;
    String name;
    String surname;
    String phone;
    Address address;
}
