package com.example.bigbisort_be.core.seller.sign_up.request;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellerSigninResponsetBean {
    private String status;
    private String message;
}
