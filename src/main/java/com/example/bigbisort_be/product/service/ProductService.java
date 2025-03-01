package com.example.bigbisort_be.product.service;

import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.exception.ProductAlreadyExistException;
import com.example.bigbisort_be.product.bean.request.ProductRequestBean;
import com.example.bigbisort_be.product.bean.response.ProductResponseBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface ProductService {
    CollectionModel<ProductResponseBean> addProduct(List<ProductRequestBean> productRequestBean) throws ProductAlreadyExistException;

    ProductResponseBean getProduct(UUID productId) throws IdNotFoundException;

}
