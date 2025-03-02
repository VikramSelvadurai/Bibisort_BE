package com.example.bigbisort_be.core.contact.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactRequestBean {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, message ="Name must be min 3 characters long")
    @Size(max = 30, message = "Name reached maximum character")
    private String name;
    @NotEmpty(message = "email cannot be empty")
    private String email;
    @Size(min = 1, message = "Message must be min 3 characters long")
    @Size(max = 255,message = "Message reached maximum character")
    private String message;
    private String phoneNumber;
    private String city;

}
