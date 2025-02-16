package com.example.bigbisort_be.contact.controller;

import com.example.bigbisort_be.contact.request.ContactRequestBean;
import com.example.bigbisort_be.contact.response.ContactResponseBean;
import com.example.bigbisort_be.contact.service.ContactServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/contact")
@RequiredArgsConstructor
public class ContactController {
    private final ContactServiceImpl contactService;

    @PostMapping("/save-contact-info")
    public ContactResponseBean addContactInfo(@RequestBody @Valid ContactRequestBean contactRequestBean) {
       return contactService.addContactInfo(contactRequestBean);
    }

}
