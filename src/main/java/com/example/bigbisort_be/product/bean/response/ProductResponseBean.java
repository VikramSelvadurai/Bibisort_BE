package com.example.bigbisort_be.product.bean.response;

import com.example.bigbisort_be.varieties.bean.response.VarietiesResponseBean;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductResponseBean extends RepresentationModel<ProductResponseBean> {

    private UUID productId;

    private String productName;

    private String category;

    private String subcategory;

    private String description;

    private String image_url;

    private List<VarietiesResponseBean> varietiesList;
}
