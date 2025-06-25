package com.example.bigbisort_be.core.watch_list.controller;

import com.example.bigbisort_be.core.watch_list.bean.request.WatchListRequestBean;
import com.example.bigbisort_be.core.watch_list.bean.response.WatchListResponseBean;
import com.example.bigbisort_be.core.watch_list.service.WatchListService;
import com.example.bigbisort_be.exception.ProductIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/watch-list")
public class WatchListController {

    private final WatchListService watchListService;

    @PostMapping("/add")
    public Map<String,String> addWatchList(@RequestBody WatchListRequestBean watchListRequestBean) throws ProductIdNotFoundException {
        return watchListService.addProductWatchList(watchListRequestBean);

    }

    @PostMapping("/filter")
    public PagedModel<WatchListResponseBean> filterWatchList(@RequestBody WatchListRequestBean watchListRequestBean, Pageable pageable) {
        return watchListService.filterWatchList(watchListRequestBean,pageable);

    }

}
