package com.example.bigbisort_be.persistence.order.buyer.entity;

import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "BUYER_ORDER",indexes = @Index(columnList = "id"))
public class BuyerOrderEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date orderDate;

    private String paymentMethod;

    private String billingCompanyName;

    private String quantity;

    private String shippingName;

    @Temporal(TemporalType.TIMESTAMP)
    private String estimationDateOfArrival;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private BuyerEntity buyerEntity;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "ORDERS_PRODUCT_JOIN",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "buyer_order_id"))
    private Set<ProductsEntity> productsEntitySet = new LinkedHashSet<>();

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        BuyerOrderEntity buyerOrderEntity = (BuyerOrderEntity) o;
        return buyerOrderEntity.id.equals(this.id);
    }

}
