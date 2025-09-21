package com.example.bigbisort_be.persistence.admin.entity;

import com.example.bigbisort_be.persistence.audit.model.entity.AuditEntity;
import com.example.bigbisort_be.persistence.contact.model.entity.ContactEntity;
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
@Table(name = "admin",indexes ={@Index(columnList = "id,name")})
public class AdminEntity extends AuditEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email",nullable = false)
    private String email;

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
        AdminEntity adminEntity = (AdminEntity) o;
        return adminEntity.id.equals(this.id);
    }
}
