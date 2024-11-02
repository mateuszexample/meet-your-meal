package pl.molsztyn.meetyourmeal.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    Set<Cart> carts;

    public Set<Cart> getCarts() {
        return Objects.isNull(carts) ? new HashSet<>() : carts;
    }
}
