package com.example.bigbisort_be.product.bean.request;

import com.example.bigbisort_be.varieties.bean.request.VarietiesRequestBean;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequestBean {

//    private UUID productId;

    private String productName;

    private String category;

    private String subcategory;

    private String description;

    private String image_url;

    private List<VarietiesRequestBean> varietiesRequestBeanList;

}
