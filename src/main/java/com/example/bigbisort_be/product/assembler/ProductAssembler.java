package com.example.bigbisort_be.product.assembler;

import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import com.example.bigbisort_be.product.bean.response.ProductResponseBean;
import com.example.bigbisort_be.varieties.assembler.VarietiesAssembler;
import com.example.bigbisort_be.varieties.bean.response.VarietiesResponseBean;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductAssembler implements RepresentationModelAssembler<ProductsEntity, ProductResponseBean> {

    private final VarietiesAssembler varietiesAssembler;

    @Override
    public ProductResponseBean toModel(ProductsEntity entity) {
        return buildModel(entity);
    }

    @Override
    public CollectionModel<ProductResponseBean> toCollectionModel(Iterable<? extends ProductsEntity> entities) {
        List<ProductResponseBean> productResponseBeansList = new ArrayList<>();
        for (ProductsEntity entity : entities) {
            productResponseBeansList.add(toModel(entity));
        }
        return CollectionModel.of(productResponseBeansList);
    }

    public ProductResponseBean buildModel(ProductsEntity productsEntity){
        ProductResponseBean productResponseBean = ProductResponseBean.builder()
                .productId(productsEntity.getId())
                .productName(productsEntity.getProductName())
                .category(productsEntity.getCategory())
                .subcategory(productsEntity.getSubcategory())
                .image_url(productsEntity.getImage_url())
                .description(productsEntity.getDescription())
                .build();
        if(!productsEntity.getVarietiesEntitySet().isEmpty()){
            productResponseBean.setVarietiesList(varietiesAssembler.toLists(productsEntity.getVarietiesEntitySet()));
        }

        return productResponseBean;
    }
}
