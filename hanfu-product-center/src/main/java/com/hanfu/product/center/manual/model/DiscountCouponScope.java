package com.hanfu.product.center.manual.model;

public class DiscountCouponScope {
    public static enum ScopeTypeEnum {
        All_USER("allUser"),
        VIP_USER("vipUser");
        private String orderType;
        ScopeTypeEnum(String orderType) {
            this.orderType = orderType;
        }
        public String getOrderType() {
            return this.orderType;
        }

        public static ScopeTypeEnum getOrderTypeEnum(String orderType) {
            for(ScopeTypeEnum item : ScopeTypeEnum.values()) {
                if (item.orderType.equals(orderType)) {
                    return item;
                }
            }
            return All_USER;
        }
    }
}
