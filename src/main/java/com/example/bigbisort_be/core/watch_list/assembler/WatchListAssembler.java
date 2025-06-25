package com.example.bigbisort_be.core.watch_list.assembler;

import com.example.bigbisort_be.core.product.assembler.ProductAssembler;
import com.example.bigbisort_be.core.watch_list.bean.response.WatchListResponseBean;
import com.example.bigbisort_be.persistence.watch_list.entity.WatchListEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WatchListAssembler implements RepresentationModelAssembler<WatchListEntity, WatchListResponseBean> {

    private final ProductAssembler productAssembler;
    @Override
    public WatchListResponseBean toModel(WatchListEntity entity) {
        return buildModel(entity);
    }

    @Override
    public CollectionModel<WatchListResponseBean> toCollectionModel(Iterable<? extends WatchListEntity> entities) {
        List<WatchListResponseBean> watchListResponseBeanList = new ArrayList<>();
        for (WatchListEntity entity : entities) {
            watchListResponseBeanList.add(toModel(entity));
        }
        return CollectionModel.of(watchListResponseBeanList);
    }

    public WatchListResponseBean buildModel(WatchListEntity entity) {
        return WatchListResponseBean.builder()
                .productResponseBeans(productAssembler.toModel(entity.getProductEntity()))
                .buyerId(entity.getBuyerId()).build();
    }

}
