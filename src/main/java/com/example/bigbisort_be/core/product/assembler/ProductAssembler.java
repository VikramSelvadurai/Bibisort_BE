package com.example.bigbisort_be.core.product.assembler;

import com.example.bigbisort_be.persistence.product.entity.ProductEntity;
import com.example.bigbisort_be.core.product.bean.response.ProductResponseBean;
import com.example.bigbisort_be.core.varieties.assembler.VarietiesAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductAssembler implements RepresentationModelAssembler<ProductEntity, ProductResponseBean> {

    private final VarietiesAssembler varietiesAssembler;

    @Override
    public ProductResponseBean toModel(ProductEntity entity) {
        return buildModel(entity);
    }

    @Override
    public CollectionModel<ProductResponseBean> toCollectionModel(Iterable<? extends ProductEntity> entities) {
        List<ProductResponseBean> productResponseBeansList = new ArrayList<>();
        for (ProductEntity entity : entities) {
            productResponseBeansList.add(toModel(entity));
        }
        return CollectionModel.of(productResponseBeansList);
    }

    public ProductResponseBean buildModel(ProductEntity productEntity){
        ProductResponseBean productResponseBean = ProductResponseBean.builder()
                .productId(productEntity.getId())
                .productName(productEntity.getProductName())
                .category(productEntity.getCategory())
                .subcategory(productEntity.getSubcategory())
                .image_url(productEntity.getImage_url())
                .description(productEntity.getDescription())
                .build();
        if(!productEntity.getVarietiesEntitySet().isEmpty()){
            productResponseBean.setVarietiesList(varietiesAssembler.toLists(productEntity.getVarietiesEntitySet()));
        }

        return productResponseBean;
    }
}
