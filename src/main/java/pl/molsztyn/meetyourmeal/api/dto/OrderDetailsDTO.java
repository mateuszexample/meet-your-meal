package pl.molsztyn.meetyourmeal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.molsztyn.meetyourmeal.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {

    private String orderNumber;
    private String receivedTime;
    private String toCancelTime;
    private BigDecimal orderValue;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> orderItems;
}
