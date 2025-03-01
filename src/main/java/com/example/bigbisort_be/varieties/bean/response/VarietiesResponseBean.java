package com.example.bigbisort_be.varieties.bean.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class VarietiesResponseBean extends RepresentationModel<VarietiesResponseBean> {

    private UUID varietyId;

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
