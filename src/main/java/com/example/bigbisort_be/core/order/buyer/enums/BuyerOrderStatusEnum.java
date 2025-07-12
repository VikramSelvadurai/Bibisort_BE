package com.example.bigbisort_be.core.order.buyer.enums;

public enum BuyerOrderStatusEnum {

    IN_TRANSIT("In-Transit"),
    CANCELLED("Cancelled"),
    RETURNED("Returned"),
    DELIVERED("Delivered");

    private final String type;

    BuyerOrderStatusEnum(String type) {
        this.type = type;
    }

    public String getSignTypValue() {
        return type;
    }
    public static String getValueByKey(String key) {
        for (BuyerOrderStatusEnum buyerOrderStatusEnum : values()) {
            if (buyerOrderStatusEnum.name().equalsIgnoreCase(key)) {
                return buyerOrderStatusEnum.getSignTypValue();
            }
        }
        return key;
    }
}
