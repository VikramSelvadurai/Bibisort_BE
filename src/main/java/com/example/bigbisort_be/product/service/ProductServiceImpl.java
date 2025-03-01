package com.example.bigbisort_be.product.service;

import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.exception.ProductAlreadyExistException;
import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.product.model.ProductsRepositoryService;
import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import com.example.bigbisort_be.product.assembler.ProductAssembler;
import com.example.bigbisort_be.product.bean.request.ProductRequestBean;
import com.example.bigbisort_be.product.bean.response.ProductResponseBean;
import com.example.bigbisort_be.varieties.assembler.VarietiesAssembler;
import com.example.bigbisort_be.varieties.bean.response.VarietiesResponseBean;
import com.example.bigbisort_be.varieties.service.VarietiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final VarietiesAssembler varietiesAssembler;

    private final static String PRODUCT_ALREADY_EXIST ="Product already Exist ";

    private final VarietiesService varietiesService;
    private final ProductsRepositoryService productsRepositoryService;
    private final ProductAssembler productAssembler;


    @Override
    public CollectionModel<ProductResponseBean> addProduct(List<ProductRequestBean> productRequestBeanList) throws ProductAlreadyExistException {
        List<ProductsEntity> productsEntities = new ArrayList<>();
        for (ProductRequestBean productRequestBean : productRequestBeanList) {
            if(productsRepositoryService.existsByProductNameIgnoreCase(productRequestBean.getProductName())){
                throw new ProductAlreadyExistException("Product '"+productRequestBean.getProductName()+"' already Exist");
            }
            ProductsEntity productsEntity = ProductsEntity.builder()
                    .productName(productRequestBean.getProductName())
                    .description(productRequestBean.getDescription())
                    .category(productRequestBean.getCategory())
                    .subcategory(productRequestBean.getSubcategory())
                    .image_url(productRequestBean.getImage_url())
                    .build();
            productsEntity = productsRepositoryService.save(productsEntity);
            if(!productRequestBean.getVarietiesRequestBeanList().isEmpty()){
                Set<VarietiesEntity> varietiesEntitySet =varietiesService.addVarietiesWithProduct(productRequestBean.getVarietiesRequestBeanList(),productsEntity).stream().collect(Collectors.toSet());
                productsEntity.setVarietiesEntitySet(varietiesEntitySet);
                productsEntity = productsRepositoryService.save(productsEntity);
            }
            productsEntities.add(productsEntity);
        }
        return productAssembler.toCollectionModel(productsEntities);
    }

    @Override
    public ProductResponseBean getProduct(UUID productId) throws IdNotFoundException {
        ProductResponseBean productResponseBean = null;
        Optional<ProductsEntity> productsEntityOptional = productsRepositoryService.findById(productId);
        if(productsEntityOptional.isPresent()){
            productResponseBean = productAssembler.toModel(productsEntityOptional.get());
        }else {
            throw new IdNotFoundException("Product '"+productId+"' not fount");
        }
        return productResponseBean;
    }
}
