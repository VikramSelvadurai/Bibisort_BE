package com.example.bigbisort_be.core.product.utils;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@UtilityClass
public class CriteriaUtils {

    public static final char ESCAPE_CHAR = '\\';
    /**
     * Escapes special characters used in SQL LIKE queries.
     *
     * @param input the input string to escape
     * @return the escaped string
     */
    public static String escapeForLike(String input) {
        if (input == null) {
            return null;
        }
        // Escape special characters: _ and %
        return input.replace("\\",  ESCAPE_CHAR + "\\") // Escape backslash first
                .replace("_",  ESCAPE_CHAR + "_")
                .replace("%", ESCAPE_CHAR + "%");
    }

    public static String formatSearchText(String searchText) {
        return searchText.replace("\\", "\\\\").replace("_", "\\_");
    }
}
