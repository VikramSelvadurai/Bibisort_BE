package com.example.bigbisort_be.common.enums;

public enum AuthenticationType {

    BUYER("Buyer"),
    SELLER("Seller"),
    ADMIN("Admin");

    private final String type;

    AuthenticationType(String type) {
        this.type = type;
    }

    public String getSignTypValue() {
        return type;
    }
    public static String getValueByKey(String key) {
        for (AuthenticationType authenticationType : values()) {
            if (authenticationType.name().equalsIgnoreCase(key)) {
                return authenticationType.getSignTypValue();
            }
        }
        return key;
    }
}
