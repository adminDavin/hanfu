package com.hanfu.product.center.model;

import java.io.Serializable;
import java.util.List;


public class OpenDetails implements Serializable {
    private  Integer  sum;
    private List<Open> Open;

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public List<com.hanfu.product.center.model.Open> getOpen() {
        return Open;
    }

    public void setOpen(List open) {
        Open = open;
    }
}