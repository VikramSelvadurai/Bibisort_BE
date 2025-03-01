package com.example.bigbisort_be.product.controller;


import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.exception.ProductAlreadyExistException;
import com.example.bigbisort_be.product.bean.request.ProductRequestBean;
import com.example.bigbisort_be.product.bean.response.ProductResponseBean;
import com.example.bigbisort_be.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
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
}
