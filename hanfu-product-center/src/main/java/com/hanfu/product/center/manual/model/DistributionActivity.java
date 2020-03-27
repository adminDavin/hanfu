package com.hanfu.product.center.manual.model;

import io.swagger.models.auth.In;

public class DistributionActivity {
    private String name;
    private Integer ratio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRatio() {
        return ratio;
    }

    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }
}
