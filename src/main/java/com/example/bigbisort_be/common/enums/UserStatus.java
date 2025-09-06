package com.example.bigbisort_be.common.enums;

public enum UserStatus {
    REGISTERED("Registered"),
    ACTIVE("Active"),
    REVOKED("Revoked"),
    LOCKED("Locked");

    private final String value;

    private UserStatus(String value) {
        this.value = value;
    }

    public static UserStatus getEnumByName(String enumName) {
        UserStatus[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            UserStatus e = var1[var3];
            if (e.name().equals(enumName)) {
                return e;
            }
        }

        return null;
    }

    public String value() {
        return this.value;
    }
}
