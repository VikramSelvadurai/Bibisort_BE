package com.example.bigbisort_be.core.varieties.controller;


import com.example.bigbisort_be.core.varieties.bean.response.VarietiesResponseBean;
import com.example.bigbisort_be.core.varieties.service.VarietiesService;
import com.example.bigbisort_be.core.varieties.bean.request.VarietiesFilterRequestBean;
import com.example.bigbisort_be.core.varieties.bean.request.VarietiesRequestBean;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/varieties")
@RequiredArgsConstructor
public class VarietiesController {

    private final VarietiesService varietiesService;

    @PostMapping("/add")
    public CollectionModel<VarietiesResponseBean> addVarieties(@RequestBody List<VarietiesRequestBean> varietiesRequestBean){
        return varietiesService.addVarieties(varietiesRequestBean);
    }

    @GetMapping("/{varietiesId}")
    public VarietiesResponseBean getVarieties(@RequestParam UUID varietiesId){
        return varietiesService.getVarieties(varietiesId);
    }

    @PostMapping("/filter")
    public PagedModel<VarietiesResponseBean> getVarietiesFilter(@RequestBody VarietiesFilterRequestBean varietiesFilterRequestBean, Pageable pageable){
        return varietiesService.getVarietiesFilter(varietiesFilterRequestBean,pageable);
    }

}
