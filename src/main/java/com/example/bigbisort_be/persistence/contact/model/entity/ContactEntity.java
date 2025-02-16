package com.example.bigbisort_be.persistence.contact.model.entity;

import com.example.bigbisort_be.persistence.audit.model.entity.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "CONTACT",indexes = @Index(columnList = "name"))
public class ContactEntity extends AuditEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "Ph_no")
    private String phoneNumber;

    @Column(name = "message", length = 250, columnDefinition = "varchar(250) default ''", nullable = false)
    private String message;

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ContactEntity contactEntity = (ContactEntity) o;
        return contactEntity.id.equals(this.id);
    }

}
