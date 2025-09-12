package com.example.bigbisort_be.persistence.product.entity;

import com.example.bigbisort_be.persistence.audit.model.entity.AuditEntity;
import com.example.bigbisort_be.persistence.order.buyer.entity.BuyerOrderEntity;
import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import com.example.bigbisort_be.persistence.watch_list.entity.WatchListEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "PRODUCT",indexes = @Index(columnList = "productName"))
@FieldNameConstants
public class ProductEntity extends AuditEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private String productName;

    private String category;

    private String subcategory;

    private String description;

    private String image_url;

    private BigInteger quantity;

    @JsonBackReference
    @OneToMany(
            mappedBy = "productEntity",
            orphanRemoval = true,
            cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    private Set<VarietiesEntity> varietiesEntitySet=new LinkedHashSet<>();;

    @JsonIgnore
    @ManyToMany(mappedBy = "productEntitySet")
    @Builder.Default
    private Set<BuyerOrderEntity> buyerOrderEntitySet = new HashSet<>();

    @OneToOne(mappedBy = "productEntity")
    private WatchListEntity watchListEntity;

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ProductEntity productEntity = (ProductEntity) o;
        return productEntity.id.equals(this.id);
    }
}
