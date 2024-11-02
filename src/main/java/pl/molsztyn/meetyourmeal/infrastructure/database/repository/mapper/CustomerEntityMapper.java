package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.Cart;
import pl.molsztyn.meetyourmeal.domain.Customer;
import pl.molsztyn.meetyourmeal.domain.OrderItem;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CartEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CustomerEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderItemEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    @Mapping(source = "carts", target = "carts", qualifiedByName = "mapCartsFromEntity")
    Customer mapFromEntity(CustomerEntity customerEntity);

    @Named("mapCartsFromEntity")
    default Set<Cart> mapCartsFromEntity(Set<CartEntity> cartEntities) {
        return cartEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    @Mapping(target = "customer.carts", ignore = true)
    @Mapping(target = "restaurant.address", ignore = true)
    @Mapping(target = "restaurant.menu", ignore = true)
    @Mapping(target = "restaurant.restaurantOwner", ignore = true)
    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "mapOrderItemsFromEntity")
    Cart mapFromEntity(CartEntity cartEntity);

    @Named("mapOrderItemsFromEntity")
    default Set<OrderItem> mapOrderItemsFromEntity(Set<OrderItemEntity> orderItemEntities) {
        return orderItemEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "food.restaurant", ignore = true)
    OrderItem mapFromEntity(OrderItemEntity orderItemEntity);
}