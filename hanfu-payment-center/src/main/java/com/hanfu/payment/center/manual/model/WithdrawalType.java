package com.hanfu.payment.center.manual.model;

public class WithdrawalType {
    public static enum DiscoverTypeEnum {

        WECHART("wechart"),//微信
        APPCHART("appchart"),
        APPALIPAY("appalipay");//支付宝
        private String discoverType;

        private DiscoverTypeEnum(String discoverType) {
            this.discoverType = discoverType;
        }

        public String getDiscoverType() {
            return discoverType;
        }


        public void setDiscoverType(String discoverType) {
            this.discoverType = discoverType;
        }


        public static DiscoverTypeEnum getDiscoverTypeEnum(String discoverType) {
            for(DiscoverTypeEnum d:DiscoverTypeEnum.values()) {
                if(d.getDiscoverType().equals(discoverType)) {
                    return d;
                }
            }
            return APPALIPAY;
        }
    }

    public static enum DiscoverStateEnum {

        PENDING("pending"),//待处理
        COMPLETE("complete"),//完成
        CANCEL("cancel");//取消
        private String discoverType;

        private DiscoverStateEnum(String discoverType) {
            this.discoverType = discoverType;
        }

        public String getDiscoverType() {
            return discoverType;
        }


        public void setDiscoverType(String discoverType) {
            this.discoverType = discoverType;
        }


        public static DiscoverStateEnum getDiscoverTypeEnum(String discoverType) {
            for(DiscoverStateEnum d: DiscoverStateEnum.values()) {
                if(d.getDiscoverType().equals(discoverType)) {
                    return d;
                }
            }
            return PENDING;
        }
    }
}
