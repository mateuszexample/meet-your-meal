package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.Cart;
import pl.molsztyn.meetyourmeal.domain.CartItem;
import pl.molsztyn.meetyourmeal.domain.Customer;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CartEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CartItemEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CustomerEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    @Mapping(source = "carts", target = "carts", qualifiedByName = "mapCartsFromEntity")
    @Mapping(target = "address.customer", ignore = true )
    Customer mapFromEntity(CustomerEntity customerEntity);

    @Named("mapCartsFromEntity")
    default Set<Cart> mapCartsFromEntity(Set<CartEntity> cartEntities) {
        return cartEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    @Mapping(target = "customer.carts", ignore = true)
    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "restaurant.address", ignore = true)
    @Mapping(target = "restaurant.menu", ignore = true)
    @Mapping(target = "restaurant.restaurantOwner", ignore = true)
    @Mapping(source = "cartItems", target = "cartItems", qualifiedByName = "mapCartItemsFromEntity")
    Cart mapFromEntity(CartEntity cartEntity);

    @Named("mapCartItemsFromEntity")
    default List<CartItem> mapCartItemsFromEntity(Set<CartItemEntity> cartItemEntities) {
        return cartItemEntities.stream()
                .map(this::mapFromEntity)
                .sorted(Comparator.comparing(CartItem::getCartItemId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Mapping(target = "cart.totalPrice", ignore = true)
    @Mapping(target = "cart.customer", ignore = true)
    @Mapping(target = "cart.restaurant", ignore = true)
    @Mapping(target = "cart.cartItems", ignore = true)
    @Mapping(target = "food.restaurant", ignore = true)
    CartItem mapFromEntity(CartItemEntity cartItemEntity);

    CustomerEntity mapToEntity(Customer customer);
}