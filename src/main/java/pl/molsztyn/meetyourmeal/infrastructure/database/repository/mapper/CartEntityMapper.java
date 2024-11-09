package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.Cart;
import pl.molsztyn.meetyourmeal.domain.OrderItem;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CartEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderItemEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartEntityMapper {
    CartEntity mapToEntity(Cart cart);

    @Mapping(target = "customer.name", ignore = true)
    @Mapping(target = "customer.surname", ignore = true)
    @Mapping(target = "customer.phone", ignore = true)
    @Mapping(target = "customer.address", ignore = true)
    @Mapping(target = "customer.carts", ignore = true)
    @Mapping(target = "restaurant.name", ignore = true)
    @Mapping(target = "restaurant.imageData", ignore = true)
    @Mapping(target = "restaurant.address", ignore = true)
    @Mapping(target = "restaurant.restaurantOwner", ignore = true)
    @Mapping(target = "restaurant.deliveryAreas", ignore = true)
    @Mapping(target = "restaurant.foodCategories", ignore = true)
    @Mapping(target = "restaurant.menu", ignore = true)
    @Mapping(source = "orderItems", target = "orderItems", qualifiedByName = "mapOrderItemsFromEntity")
    Cart mapFromEntity(CartEntity cartSaved);

    @Named("mapOrderItemsFromEntity")
    default List<OrderItem> mapOrderItemsFromEntity(Set<OrderItemEntity> orderItemEntities) {
        return orderItemEntities.stream()
                .map(this::mapFromEntity)
                .sorted(Comparator.comparing(OrderItem::getOrderItemId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Mapping(target = "food.restaurant", ignore = true)
    @Mapping(target = "cart.totalPrice", ignore = true)
    @Mapping(target = "cart.customer", ignore = true)
    @Mapping(target = "cart.restaurant", ignore = true)
    @Mapping(target = "cart.orderItems", ignore = true)
    OrderItem mapFromEntity(OrderItemEntity orderItemEntity);
}
