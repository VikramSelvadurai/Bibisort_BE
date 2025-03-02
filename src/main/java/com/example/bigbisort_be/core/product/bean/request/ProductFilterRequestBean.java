package com.example.bigbisort_be.core.product.bean.request;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductFilterRequestBean {
    private String productName;
    private String category;
}
