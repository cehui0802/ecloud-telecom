package com.telecom.module.setmeal.core.entity;


import com.telecom.ecloudframework.base.core.model.GBaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptimizeCard extends GBaseModel implements Serializable{
    private String id;

    private String classifyId;

    private String name;

    private String constitute;

    private Integer flow;

    private String unit;

    private String voiceDuration;

    private String broadBand;

    private String itv;

    private String secondBroadBand;


}