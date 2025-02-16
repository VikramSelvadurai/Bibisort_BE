package com.example.bigbisort_be.persistence.signup.seller_signup.entity;

import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerSignupEntity;
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
public class SellerSignupEntity {
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

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SellerSignupEntity sellerSignupEntity = (SellerSignupEntity) o;
        return sellerSignupEntity.id.equals(this.id);
    }
}
