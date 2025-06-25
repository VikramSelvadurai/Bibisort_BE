package com.example.bigbisort_be.core.watch_list.bean.response;

import com.example.bigbisort_be.core.product.bean.response.ProductResponseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchListResponseBean extends RepresentationModel<WatchListResponseBean> {

    private UUID buyerId;
    private UUID buyerName;
    private ProductResponseBean productResponseBeans;
}
