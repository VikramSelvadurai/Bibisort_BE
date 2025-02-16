package com.example.bigbisort_be.persistence.contact.model.repository;

import com.example.bigbisort_be.persistence.contact.model.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity,UUID> {

}
