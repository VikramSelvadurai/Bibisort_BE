package com.example.bigbisort_be.persistence.audit.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serializable;
import java.util.Date;

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
    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;


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
