package com.example.bigbisort_be.common.bean;

public enum SignTypeEnum {

    BUYER("Buyer"),
    SELLER("Seller"),
    ADMIN("Admin");

    private final String type;

    SignTypeEnum(String type) {
        this.type = type;
    }

    public String getSignTypValue() {
        return type;
    }
    public static String getValueByKey(String key) {
        for (SignTypeEnum signTypeEnum : values()) {
            if (signTypeEnum.name().equalsIgnoreCase(key)) {
                return signTypeEnum.getSignTypValue();
            }
        }
        return key;
    }
}
