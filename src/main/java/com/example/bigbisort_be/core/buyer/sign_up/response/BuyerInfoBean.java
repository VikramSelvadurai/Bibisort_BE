package com.example.bigbisort_be.core.buyer.sign_up.response;

import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BuyerInfoBean {
    private String name;
    private String email;
    private String phone;
}
