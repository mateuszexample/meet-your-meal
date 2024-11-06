package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.molsztyn.meetyourmeal.api.dto.OrderItemDTO;
import pl.molsztyn.meetyourmeal.domain.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "foodId", expression = "java(orderItem.getFood().getFoodId())")
    @Mapping(target = "foodName", expression = "java(orderItem.getFood().getName())")
    OrderItemDTO mapToDTO(OrderItem orderItem);
}
