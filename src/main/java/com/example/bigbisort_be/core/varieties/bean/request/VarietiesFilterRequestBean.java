package com.example.bigbisort_be.core.varieties.bean.request;

import lombok.*;

import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VarietiesFilterRequestBean {

    private UUID productId;
    private String varietyName;

}
