package com.example.bigbisort_be.common.bean;

import com.example.bigbisort_be.common.enums.AuthenticationType;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Component
@ToString
public class UserAuthenticationDetails {
    @Builder.Default private boolean isAccountNonExpired = true;
    @Builder.Default private boolean isAccountNonLocked = true;
    @Builder.Default private boolean isCredentialsNonExpired = true;
    @Builder.Default private boolean isEnabled = true;
    /**
     * Consent agreement boolean for the user
     */
    @Builder.Default private boolean consentAgreed = true;
    private List<String> group;
    private Set<String> action;
    private String firstName;
    private String userId;
    private String userName;
    private String lastName;
    private String email;
    private String ipAddress;
    private boolean licenseValidityStatus;
    private AuthenticationType authenticationType;
    private UUID commonUserId;
}
