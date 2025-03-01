package com.example.bigbisort_be.persistence.signup.buyer_signup.entity;

import com.example.bigbisort_be.persistence.order.buyer.entity.BuyerOrderEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "BUYER",indexes = @Index(columnList = "name"))
public class BuyerEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private String name;

    private String userName;

    private String email;

    private String phone;

    private String address;

    private String state;

    private String city;

    private String zip;

    private String country;

    private String password;

    @JsonBackReference
    @OneToMany(
            mappedBy = "buyerEntity",
            orphanRemoval = true,
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    private Set<BuyerOrderEntity> buyerOrderEntitySet;



    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        BuyerEntity buyerEntity = (BuyerEntity) o;
        return buyerEntity.id.equals(this.id);
    }



}
