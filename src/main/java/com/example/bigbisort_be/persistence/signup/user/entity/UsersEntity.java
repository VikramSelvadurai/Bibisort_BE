package com.example.bigbisort_be.persistence.signup.user.entity;

import com.example.bigbisort_be.common.enums.AuthenticationType;
import com.example.bigbisort_be.common.enums.UserStatus;
import com.example.bigbisort_be.persistence.admin.entity.AdminEntity;
import com.example.bigbisort_be.persistence.audit.model.entity.AuditEntity;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import com.example.bigbisort_be.persistence.signup.seller_signup.entity.SellerEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Users",indexes = @Index(columnList = "name"))
public class UsersEntity extends AuditEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private String name;
    @Column(name = "user_name")
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String sPhrase;
    private UUID commonUserId;

    @JsonIgnore
    @Column(name = "old_s_phrase", columnDefinition = "TEXT")
    private String oldSPhrase;

    private String businessJustification;

    @NotNull(message = "{bean.validation.create-user.authentication-type.required}")
    @Enumerated(EnumType.STRING)
    private AuthenticationType authenticationType;

    @Column(name = "lock_user")
    private Boolean lock = false;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    private String securityQues1;
    private String securityAns1;
    private String securityQues2;
    private String securityAns2;

    @Column(name = "profile")
//    @Type(type = "org.hibernate.type.ImageType")
    @Lob
    private byte[] profile;

    @Builder.Default private boolean sPhraseResetRequested = false;

    @OneToOne(mappedBy = "usersEntity", orphanRemoval = true)
    private BuyerEntity buyerEntity;

    @OneToOne(mappedBy = "usersEntity", orphanRemoval = true)
    private SellerEntity sellerEntity;

    @OneToOne(mappedBy = "usersEntity", orphanRemoval = true)
    private AdminEntity adminEntity;
//    /**
//     * Status of user consent agreement
//     */
//    @Enumerated(EnumType.STRING)
//    private UserConsentStatusEnum consentStatus;

//    /**
//     * Time of last consent agreement
//     */
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date consentUpdateDate;

//    /**
//     * Version Id of last agreed consent
//     */
//    @Type(type = "uuid-char")
//    private UUID consentVersionId;

//    @JsonBackReference
//    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable(
//            name = "ADS_user_group_mapping",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "group_id"))
//    private Set<UserGroupEntity> userGroupEntities;

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        UsersEntity usersEntity = (UsersEntity) o;
        return usersEntity.id.equals(this.id);
    }

}
