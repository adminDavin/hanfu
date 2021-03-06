package com.hanfu.dichan.center.model;

public class General {
    public static enum GeneralTypeEnum {

        GENERAL_TYPE_ENUM("general"),
        CATEGORT_TYPE_ENUM("category");

        private String generalType;

        private GeneralTypeEnum(String discoverType) {
            this.generalType = discoverType;
        }

        public String getGeneralType() {
            return this.generalType;
        }


        public void setGeneralType(String discoverType) {
            this.generalType = discoverType;
        }


        public static GeneralTypeEnum getGeneralTypeEnum(String generalType) {
            for(GeneralTypeEnum d:GeneralTypeEnum.values()) {
                if(d.getGeneralType().equals(generalType)) {
                    return d;
                }
            }
            return GENERAL_TYPE_ENUM;
        }
    }
}
