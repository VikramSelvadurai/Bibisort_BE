package com.example.bigbisort_be.persistence.watch_list.entity;

import com.example.bigbisort_be.persistence.audit.model.entity.AuditEntity;
import com.example.bigbisort_be.persistence.product.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "WATCH_LIST",indexes = @Index(columnList = "id"))
@FieldNameConstants
public class WatchListEntity extends AuditEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    private UUID buyerId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "WATCH_LIST_PRODUCT",
            joinColumns =
                    { @JoinColumn(name = "watch_list_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "product_id", referencedColumnName = "id") })
    private ProductEntity productEntity;

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        WatchListEntity watchListEntity = (WatchListEntity) o;
        return watchListEntity.id.equals(this.id);
    }


}
