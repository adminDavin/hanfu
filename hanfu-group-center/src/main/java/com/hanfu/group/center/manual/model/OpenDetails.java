package com.hanfu.group.center.manual.model;
import java.io.Serializable;
import java.util.List;

/**
 * @author:gyj
 * @date: 2020/1/9
 * @time: 11:37
 */
public class OpenDetails implements Serializable {
    private  Integer  sum;
    private List<Open> Open;

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public List<com.hanfu.group.center.manual.model.Open> getOpen() {
        return Open;
    }

    public void setOpen(List<com.hanfu.group.center.manual.model.Open> open) {
        Open = open;
    }
}
