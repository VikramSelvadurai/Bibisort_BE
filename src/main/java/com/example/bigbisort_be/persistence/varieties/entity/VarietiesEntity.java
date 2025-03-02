package com.example.bigbisort_be.persistence.varieties.entity;

import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "VARIETIES",indexes = @Index(columnList = "id"))
@FieldNameConstants
public class VarietiesEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private String varietyName;

    private String description;

    private String imageUrl;

    private String origin;

    private String grower;

    private String growingMethod;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "harvest_date")
    private Date harvestDate;

    private String harvestSeason;

    private String quality;

    private String color;

    private String size;

    private String price_per_unit;

    private String availability;

    private String certifications;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "products_id")
    private ProductsEntity productsEntity;

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        VarietiesEntity varietiesEntity = (VarietiesEntity) o;
        return varietiesEntity.id.equals(this.id);
    }

}
