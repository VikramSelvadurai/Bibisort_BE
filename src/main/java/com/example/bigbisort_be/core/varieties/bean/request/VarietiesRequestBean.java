package com.example.bigbisort_be.core.varieties.bean.request;

import lombok.*;

import java.util.Date;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class VarietiesRequestBean {

    private String varietyName;

    private String description;

    private String imageUrl;

    private String origin;

    private String grower;

    private String growingMethod;

    private Date harvestDate;

    private String harvestSeason;

    private String quality;

    private String color;

    private String size;

    private String price_per_unit;

    private String availability;

    private String certifications;

}
