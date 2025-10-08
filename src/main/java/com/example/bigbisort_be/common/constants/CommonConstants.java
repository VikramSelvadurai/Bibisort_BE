package com.example.bigbisort_be.common.constants;

import org.springframework.stereotype.Component;

@Component
public class CommonConstants {

    public final static String LOGIN_SUCCESSFULLY = "Successfully Logged in ";
    public final static String STATUS_UPDATED = "Status updated successfully";
    public final static String WATCH_LIST_ADDED = "Watch list added Successfully";
    public final static String USERNAME_ALREADY_EXIST ="exception.auth.userName.already.exist";
    public final static String USERNAME_NOT_FOUND ="exception.auth.userName.not.found";
    public final static String PHONE_NUMBER_NOT_FOUND ="exception.auth.phone_number.not.found";

    public static final String MESSAGE = "message";
    public static final String SUCCESS = "Success";
    public static final String METADATA_MISMATCH = "Metadata mismatch";
    public static final String INVALID_NAMING = "Invalid naming";
    public static final String INVALID_CREDENTIALS = "Invalid Credentials";
    public static final String ACCESS_TOKEN = "access-token";
    public static final String BLOBS = "blobs";
    public static final String SNAPSHOT_QUERY =
            "SELECT snapshot_id, element_at(summary, 'spark.app.id') as app_id FROM iceberg.%s "
                    + "where element_at(summary, 'spark.app.id') in (%s)";
    private CommonConstants() {}

}
