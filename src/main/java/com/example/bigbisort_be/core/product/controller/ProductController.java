package com.example.bigbisort_be.core.product.controller;


import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.exception.ProductAlreadyExistException;
import com.example.bigbisort_be.core.product.bean.request.ProductFilterRequestBean;
import com.example.bigbisort_be.core.product.bean.request.ProductRequestBean;
import com.example.bigbisort_be.core.product.bean.response.ProductResponseBean;
import com.example.bigbisort_be.core.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public CollectionModel<ProductResponseBean> addProduct(@RequestBody List<ProductRequestBean> productRequestBeanList) throws ProductAlreadyExistException {
        return productService.addProduct(productRequestBeanList);
    }

    @GetMapping("/{productId}")
    public ProductResponseBean getProduct(@RequestParam UUID productId) throws IdNotFoundException {
        return productService.getProduct(productId);
    }

    @PostMapping("/filter")
    public PagedModel<ProductResponseBean> getProductFilter(@RequestBody ProductFilterRequestBean productFilterRequestBean, Pageable pageable)  {
        return productService.getProductFilter(productFilterRequestBean,pageable);
    }

}
