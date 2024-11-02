package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.molsztyn.meetyourmeal.api.dto.OrderItemDTO;
import pl.molsztyn.meetyourmeal.domain.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "foodName", expression = "java(orderItem.getFood().getName())")
    @Mapping(target = "foodPrice", expression = "java(orderItem.getFood().getPrice())")
    OrderItemDTO mapToDTO(OrderItem orderItem);
}
