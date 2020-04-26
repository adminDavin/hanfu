package com.hanfu.payment.center.manual.model;

public enum StoneBalanceDis {
        EMBODY("embody"),//体现
        ORDER("order"),//订单
        TOP_UP("topUp");//充值
        private String chargeOffType;
    StoneBalanceDis(String orderType) {
            this.chargeOffType = chargeOffType;
        }
        public String getChargeOffType() {
            return this.chargeOffType;
        }

        public static StoneBalanceDis getStoneBalanceDis(String chargeOffType) {
            for(StoneBalanceDis item : StoneBalanceDis.values()) {
                if (item.chargeOffType.equals(chargeOffType)) {
                    return item;
                }
            }
            return ORDER;
        }
}
