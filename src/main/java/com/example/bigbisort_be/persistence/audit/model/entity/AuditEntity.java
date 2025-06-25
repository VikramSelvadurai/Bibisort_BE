package com.example.bigbisort_be.persistence.audit.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serializable;
import java.util.Date;
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
    @LastModifiedBy
    private String modifiedBy = "SYSTEM";

    @JdbcTypeCode(SqlTypes.UUID)
    private UUID createdId;

    @JdbcTypeCode(SqlTypes.UUID)
    private UUID modifiedId;


//    @PrePersist
//    public void prePersistCreatedID() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            if (authentication.getDetails() instanceof UserAuthenticationDetails details) {
//                createdId = details.getUserId();
//                modifiedId = details.getUserId();
//                modifiedBy = details.getUserName();
//                createdBy = details.getUserName();
//            }
//        } else {
//            createdId = ApplicationConstants.systemUuid;
//            modifiedId = ApplicationConstants.systemUuid;
//            createdBy = ApplicationConstants.SYSTEM_USER;
//            modifiedBy = ApplicationConstants.SYSTEM_USER;
//        }
//    }


//    @PreUpdate
//    public void preUpdateUpdatedID() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            if (authentication.getDetails() instanceof UserAuthenticationDetails details) {
//                modifiedId = details.getUserId();
//                modifiedBy = details.getUserName();
//            }
//        } else {
//            modifiedId = ApplicationConstants.systemUuid;
//            modifiedBy = ApplicationConstants.SYSTEM_USER;
//        }
//    }

}
