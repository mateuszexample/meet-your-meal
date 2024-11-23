package pl.molsztyn.meetyourmeal.domain.enums;

import java.util.List;

public enum OrderStatus {
    CANCEL,
    PENDING,
    CANCELLED,
    CANCELLED_BY_CUSTOMER,
    CANCELLED_BY_RESTAURANT,
    DELIVERED;

    public static List<OrderStatus> getEditableStatuses() {
        return List.of(DELIVERED, CANCELLED);
    }
}
