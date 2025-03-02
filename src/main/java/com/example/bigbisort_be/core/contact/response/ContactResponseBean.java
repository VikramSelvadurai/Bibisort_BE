package com.example.bigbisort_be.core.contact.response;


import org.springframework.hateoas.RepresentationModel;
import lombok.*;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactResponseBean extends RepresentationModel<ContactResponseBean> {
    private UUID contactId;
    private String name;
    private String email;
    private String message;
    private String phoneNumber;
    private String city;
}
