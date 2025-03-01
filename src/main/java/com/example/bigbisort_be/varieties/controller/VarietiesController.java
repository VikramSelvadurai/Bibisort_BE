package com.example.bigbisort_be.varieties.controller;


import com.example.bigbisort_be.varieties.bean.request.VarietiesRequestBean;
import com.example.bigbisort_be.varieties.bean.response.VarietiesResponseBean;
import com.example.bigbisort_be.varieties.service.VarietiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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



}
