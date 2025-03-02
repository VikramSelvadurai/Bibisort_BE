package com.example.bigbisort_be.core.contact.assembler;

import com.example.bigbisort_be.core.contact.response.ContactResponseBean;
import com.example.bigbisort_be.persistence.contact.model.entity.ContactEntity;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ContactAssembler implements RepresentationModelAssembler<ContactEntity, ContactResponseBean> {

    @Override
    public ContactResponseBean toModel(ContactEntity contactEntity) {
        return buildModel(contactEntity);
    }

    public ContactResponseBean buildModel(ContactEntity contactEntity) {
        return ContactResponseBean.builder()
                .contactId(contactEntity.getId())
                .name(contactEntity.getName())
                .message(contactEntity.getMessage())
                .phoneNumber(contactEntity.getPhoneNumber())
                .city(contactEntity.getCity())
                .email(contactEntity.getEmail())
                .build();

    }


}
