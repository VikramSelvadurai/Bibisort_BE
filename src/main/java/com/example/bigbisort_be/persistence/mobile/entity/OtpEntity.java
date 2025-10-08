package com.example.bigbisort_be.persistence.mobile.entity;

import com.example.bigbisort_be.persistence.audit.model.entity.AuditEntity;
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
@Table(name = "OTP",indexes = @Index(columnList = "id"))
@FieldNameConstants
public class OtpEntity extends AuditEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "user_name")
    private String userName;

    private String code;

    private boolean used = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        OtpEntity otpEntity = (OtpEntity) o;
        return otpEntity.id.equals(this.id);
    }

}
