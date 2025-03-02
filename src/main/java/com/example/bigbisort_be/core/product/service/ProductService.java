package com.example.bigbisort_be.core.product.service;

import com.example.bigbisort_be.core.product.bean.request.ProductRequestBean;
import com.example.bigbisort_be.core.product.bean.response.ProductResponseBean;
import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.exception.ProductAlreadyExistException;
import com.example.bigbisort_be.core.product.bean.request.ProductFilterRequestBean;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ProductService {
    CollectionModel<ProductResponseBean> addProduct(List<ProductRequestBean> productRequestBean) throws ProductAlreadyExistException;

    ProductResponseBean getProduct(UUID productId) throws IdNotFoundException;

    PagedModel<ProductResponseBean> getProductFilter(ProductFilterRequestBean productFilterRequestBean, Pageable pageable);
}
