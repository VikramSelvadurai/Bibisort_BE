package com.example.bigbisort_be.core.watch_list.bean.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WatchListRequestBean {

    private UUID buyerId;
    private UUID productId;
    private String productName;

}
