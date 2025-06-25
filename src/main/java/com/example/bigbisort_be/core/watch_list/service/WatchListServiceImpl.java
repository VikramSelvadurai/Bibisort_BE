package com.example.bigbisort_be.core.watch_list.service;

import com.example.bigbisort_be.common.MapBuilder.MapBuilder;
import com.example.bigbisort_be.common.constants.CommonConstants;
import com.example.bigbisort_be.core.watch_list.assembler.WatchListAssembler;
import com.example.bigbisort_be.core.watch_list.bean.request.WatchListRequestBean;
import com.example.bigbisort_be.core.watch_list.bean.response.WatchListResponseBean;
import com.example.bigbisort_be.exception.ProductIdNotFoundException;
import com.example.bigbisort_be.persistence.product.entity.ProductEntity;
import com.example.bigbisort_be.persistence.product.model.ProductsRepositoryService;
import com.example.bigbisort_be.persistence.watch_list.entity.WatchListEntity;
import com.example.bigbisort_be.persistence.watch_list.model.WatchListRepositoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.bigbisort_be.core.contact.service.ContactServiceImpl.MESSAGE;

@Service
@RequiredArgsConstructor
public class WatchListServiceImpl implements WatchListService {
    private final ProductsRepositoryService productsRepositoryService;
    private final WatchListRepositoryService watchListRepositoryService;
    private final PagedResourcesAssembler<WatchListEntity> watchListPagedResourcesAssembler;
    private final WatchListAssembler watchListAssembler;

    @Override
    public Map<String,String> addProductWatchList(WatchListRequestBean watchListRequestBean) throws ProductIdNotFoundException {

       ProductEntity productEntity = productsRepositoryService.findById(watchListRequestBean.getProductId());
        watchListRepositoryService.save(WatchListEntity.builder()
                .buyerId(watchListRequestBean.getBuyerId())
                .productEntity(productEntity).build());

        return MapBuilder.of(MESSAGE, CommonConstants.WATCH_LIST_ADDED);
    }

    @Override
    public PagedModel<WatchListResponseBean> filterWatchList(WatchListRequestBean watchListRequestBean, Pageable pageable) {

        Page<WatchListEntity> watchListEntityPage ;
        if(Objects.nonNull(watchListRequestBean.getBuyerId()) && StringUtils.isNotEmpty(watchListRequestBean.getProductName())){
            watchListEntityPage = watchListRepositoryService.findAllByBuyerIdAndProductEntity_ProductNameContainsIgnoreCase(watchListRequestBean.getBuyerId(), watchListRequestBean.getProductName(),pageable);
        }else {
            watchListEntityPage = watchListRepositoryService.findAllByBuyerId(watchListRequestBean.getBuyerId(),pageable);
        }
        return watchListPagedResourcesAssembler.toModel(watchListEntityPage,watchListAssembler);
    }
}
