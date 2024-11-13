package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.OrderDetails;
import pl.molsztyn.meetyourmeal.domain.OrderItem;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderDetailsEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.OrderItemEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDetailsEntityMapper {

    OrderDetailsEntity mapToEntity(OrderDetails orderDetails);

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
    OrderDetails mapFromEntity(OrderDetailsEntity orderDetailsEntity);

    @Named("mapOrderItemsFromEntity")
    default List<OrderItem> mapOrderItemsFromEntity(List<OrderItemEntity> orderItemEntities) {
        return orderItemEntities.stream()
                .map(this::mapFromEntity)
                .sorted(Comparator.comparing(OrderItem::getOrderItemId))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Mapping(target = "orderDetails.orderNumber", ignore = true)
    @Mapping(target = "orderDetails.receivedTime", ignore = true)
    @Mapping(target = "orderDetails.toCancelTime", ignore = true)
    @Mapping(target = "orderDetails.orderValue", ignore = true)
    @Mapping(target = "orderDetails.orderStatus", ignore = true)
    @Mapping(target = "orderDetails.customer", ignore = true)
    @Mapping(target = "orderDetails.restaurant", ignore = true)
    @Mapping(target = "orderDetails.orderItems", ignore = true)
//    @Mapping(source = "orderDetails", target = "orderDetails", qualifiedByName = "mapOrderDetailsFromEntity")
    OrderItem mapFromEntity(OrderItemEntity orderItemEntity);

//    @Named("mapOrderDetailsFromEntity")
//    OrderDetails mapOrderDetailsFromEntity(OrderDetailsEntity orderDetailsEntity);

}
