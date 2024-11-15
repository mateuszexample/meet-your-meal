package pl.molsztyn.meetyourmeal.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.molsztyn.meetyourmeal.domain.Cart;
import pl.molsztyn.meetyourmeal.domain.CartItem;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CartEntity;
import pl.molsztyn.meetyourmeal.infrastructure.database.entity.CartItemEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartEntityMapper {
    @Mapping(source = "cartItems", target = "cartItems", qualifiedByName = "mapCartItemsToEntity")
    CartEntity mapToEntity(Cart cart);

    @Named("mapCartItemsToEntity")
    default Set<CartItemEntity> mapCartItemsToEntity(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "cart.totalPrice", ignore = true)
    @Mapping(target = "cart.customer", ignore = true)
    @Mapping(target = "cart.restaurant", ignore = true)
    @Mapping(target = "cart.cartItems", ignore = true)
    CartItemEntity mapToEntity(CartItem cartItem);


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
    @Mapping(source = "cartItems", target = "cartItems", qualifiedByName = "mapCartItemsFromEntity")
    Cart mapFromEntity(CartEntity cartSaved);

    @Named("mapCartItemsFromEntity")
    default List<CartItem> mapCartItemsFromEntity(Set<CartItemEntity> cartItemEntities) {
        return cartItemEntities.stream()
                .map(this::mapFromEntity)
                .sorted(Comparator.comparing(CartItem::getCartItemId))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    @Mapping(target = "food.restaurant", ignore = true)
    @Mapping(target = "cart.totalPrice", ignore = true)
    @Mapping(target = "cart.customer", ignore = true)
    @Mapping(target = "cart.restaurant", ignore = true)
    @Mapping(target = "cart.cartItems", ignore = true)
    CartItem mapFromEntity(CartItemEntity cartItemEntity);
}
