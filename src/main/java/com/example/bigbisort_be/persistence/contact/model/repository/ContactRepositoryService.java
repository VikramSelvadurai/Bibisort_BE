package com.example.bigbisort_be.persistence.contact.model.repository;

import com.example.bigbisort_be.core.contact.assembler.ContactAssembler;
import com.example.bigbisort_be.core.contact.request.ContactRequestBean;
import com.example.bigbisort_be.core.contact.response.ContactResponseBean;
import com.example.bigbisort_be.persistence.contact.model.entity.ContactEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContactRepositoryService {
    private final ContactRepository contactRepository;
    private final ContactAssembler contactAssembler;

    public ContactResponseBean addContactInfo(ContactRequestBean contactRequestBean) {
        ContactEntity contactEntity = contactRepository.save(ContactEntity.builder()
                .name(contactRequestBean.getName())
                .city(contactRequestBean.getCity())
                .email(contactRequestBean.getEmail())
                .phoneNumber(contactRequestBean.getPhoneNumber())
                .message(contactRequestBean.getMessage())
                .build());
        return contactAssembler.toModel(contactRepository.save(contactEntity));
    }
}
