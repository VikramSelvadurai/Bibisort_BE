package com.example.bigbisort_be.core.varieties.service;

import com.example.bigbisort_be.persistence.product.entity.ProductsEntity;
import com.example.bigbisort_be.persistence.varieties.entity.VarietiesEntity;
import com.example.bigbisort_be.core.varieties.bean.request.VarietiesFilterRequestBean;
import com.example.bigbisort_be.core.varieties.bean.request.VarietiesRequestBean;
import com.example.bigbisort_be.core.varieties.bean.response.VarietiesResponseBean;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface VarietiesService {
    CollectionModel<VarietiesResponseBean> addVarieties(List<VarietiesRequestBean> varietiesRequestBean);

    List<VarietiesEntity> addVarietiesWithProduct(List<VarietiesRequestBean> varietiesRequestBean, ProductsEntity productsEntity);

    VarietiesResponseBean getVarieties(UUID varietiesId);

    PagedModel<VarietiesResponseBean> getVarietiesFilter(VarietiesFilterRequestBean varietiesFilterRequestBean, Pageable pageable);

}
