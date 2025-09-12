package com.example.bigbisort_be.persistence.audit.model.entity;

import com.example.bigbisort_be.common.bean.UserAuthenticationDetails;
import com.example.bigbisort_be.common.enums.AuthenticationType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FieldNameConstants
public abstract class AuditEntity implements Serializable {

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean active;

    @CreatedBy
    private String createdBy = "SYSTEM";

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_type")
    private AuthenticationType authenticationType=AuthenticationType.ADMIN;

    @LastModifiedBy
    private String modifiedBy = "SYSTEM";

    @JdbcTypeCode(SqlTypes.UUID)
    private UUID createdId;

    @JdbcTypeCode(SqlTypes.UUID)
    private UUID modifiedId;




    @PrePersist
    public void prePersistCreatedID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAuthenticationDetails details = null;
        if (authentication != null) {
            Object authenticationDetails = authentication.getDetails();
            if (authenticationDetails instanceof UserAuthenticationDetails) {
                details = (UserAuthenticationDetails) authenticationDetails;
                if (!StringUtils.isBlank(details.getUserId()) && details.getUserId() != null) {
                    createdId = UUID.fromString(details.getUserId());
                    modifiedId = UUID.fromString(details.getUserId());
                } else {
                    createdId = UUID.randomUUID();
                    modifiedId = UUID.randomUUID();
                }
                if (StringUtils.isBlank(details.getUserName())) {
                    checkAndSetUserInfo();
                } else {
                    authenticationType = details.getAuthenticationType();
                    createdBy = details.getUserName();
                    modifiedBy = details.getUserName();
                }
            }
        } else {
            createdId = UUID.randomUUID();
            modifiedId = UUID.randomUUID();
            checkAndSetUserInfo();
        }
    }

    private void checkAndSetUserInfo() {
        if (Objects.toString(createdBy, "").isEmpty()) createdBy = "SYSTEM";
        if (Objects.toString(modifiedBy, "").isEmpty()) modifiedBy = "SYSTEM";
        if (Objects.toString(authenticationType, "").isEmpty()) authenticationType = AuthenticationType.ADMIN;
    }

    @PreUpdate
    public void preUpdateUpdatedID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAuthenticationDetails details = null;
        if (authentication != null) {
            Object authenticationDetails = authentication.getDetails();
            if (authenticationDetails instanceof UserAuthenticationDetails) {
                details = (UserAuthenticationDetails) authenticationDetails;
                if (details.getUserId() != null) {
                    modifiedId = UUID.fromString(details.getUserId());
                }
                modifiedBy = details.getUserName();
            }
        } else {
            modifiedId = UUID.randomUUID();
            modifiedBy = "SYSTEM";
        }
    }

}
