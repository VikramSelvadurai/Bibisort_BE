/*
package com.example.bigbisort_be.security.core.custom_security;

import com.example.bigbisort_be.common.bean.UserAuthenticationDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
@Component
@Slf4j
public class CustomPermissionEvaluator implements PermissionEvaluator {
    public static final String HAS_ACCESS = "hasAccess";
    public static final String COMMA = ",";
    public static final String USER_AUTHENTICATION_DETAILS = "userAuthenticationDetails:";

    @Override
    public boolean hasPermission(
            Authentication authentication, Object accessType, Object permission) {
        if (authentication != null && accessType instanceof String) {
            UserAuthenticationDetails userAuthenticationDetails =
                    (UserAuthenticationDetails) authentication.getDetails();
            if (HAS_ACCESS.equalsIgnoreCase(String.valueOf(accessType))) {
                log.debug(USER_AUTHENTICATION_DETAILS + userAuthenticationDetails.getAction());
                boolean hasAccess =
                        validateAccess(
                                userAuthenticationDetails.getAction(), String.valueOf(permission).split(COMMA));
                return hasAccess;
            }
            return false;
        }
        return false;
    }

    private boolean validateAccess(Set<String> userActions, String[] actions) {
        for (String action : actions) {
            if (userActions.contains(action)) return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(
            Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
*/
