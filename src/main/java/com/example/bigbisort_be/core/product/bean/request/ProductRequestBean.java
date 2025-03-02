package com.example.bigbisort_be.core.product.bean.request;

import com.example.bigbisort_be.core.varieties.bean.request.VarietiesRequestBean;
import lombok.*;

import java.util.List;

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
