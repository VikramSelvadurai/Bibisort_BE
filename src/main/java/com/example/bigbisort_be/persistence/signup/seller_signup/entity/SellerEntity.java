package com.example.bigbisort_be.persistence.signup.seller_signup.entity;

import com.example.bigbisort_be.persistence.audit.model.entity.AuditEntity;
import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import jakarta.persistence.*;
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
@Table(name = "SELLER",indexes = @Index(columnList = "name"))
public class SellerEntity extends AuditEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String state;

    private String city;

    private String zip;

    private String country;

    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "userEntity_id", referencedColumnName = "id")
    private UsersEntity usersEntity;

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SellerEntity sellerEntity = (SellerEntity) o;
        return sellerEntity.id.equals(this.id);
    }
}
