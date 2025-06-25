package com.example.bigbisort_be.core.watch_list.service;

import com.example.bigbisort_be.core.watch_list.bean.request.WatchListRequestBean;
import com.example.bigbisort_be.core.watch_list.bean.response.WatchListResponseBean;
import com.example.bigbisort_be.exception.ProductIdNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface WatchListService {

    Map<String,String> addProductWatchList(WatchListRequestBean watchListRequestBean) throws ProductIdNotFoundException;

    PagedModel<WatchListResponseBean> filterWatchList(WatchListRequestBean watchListRequestBean, Pageable pageable);
}
