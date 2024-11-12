package pl.molsztyn.meetyourmeal.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.molsztyn.meetyourmeal.api.dto.OrderDetailsDTO;
import pl.molsztyn.meetyourmeal.api.dto.OrderItemDTO;
import pl.molsztyn.meetyourmeal.domain.OrderDetails;
import pl.molsztyn.meetyourmeal.domain.OrderItem;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    @Mapping(target = "foodId", expression = "java(orderItem.getFood().getFoodId())")
    @Mapping(target = "foodName", expression = "java(orderItem.getFood().getName())")
    OrderItemDTO mapToDTO(OrderItem orderItem);

    @Mapping(target = "receivedTime", source = "receivedTime", qualifiedByName = "formatTime")
    @Mapping(target = "toCancelTime", source = "toCancelTime", qualifiedByName = "formatTime")
    OrderDetailsDTO mapToDTO(OrderDetails orderDetails);

    @Named("formatTime")
    default String formatTime(OffsetDateTime time) {
        return time.format(formatter);
    }
}
