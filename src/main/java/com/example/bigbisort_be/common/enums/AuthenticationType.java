package com.example.bigbisort_be.common.enums;

import com.example.bigbisort_be.exception.EnumNotFound;

public enum AuthenticationType {

    BUYER("Buyer"),
    SELLER("Seller"),
    ADMIN("Admin");

    private final String type;

    AuthenticationType(String type) {
        this.type = type;
    }

    public String getAuthenticationTypeValue() {
        return type;
    }

    public static AuthenticationType getEnum(String value) {
        for (AuthenticationType e : values()) {
            if (e.type.equalsIgnoreCase(value) || e.name().equalsIgnoreCase(value)) {
                return e;
            }
        }
        throw new EnumNotFound(value);
    }
}
