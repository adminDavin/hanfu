package com.hanfu.user.center.manual.model;

public class TypeDisPlay {
    public static enum Type {
        APPLET("Applet"),
        WEB("Web"),
        UNIAPP("UniApp");

        private String type;
        Type(String type) {
            this.type = type;
        }
        public String getType() {
            return this.type;
        }

        public static Type getTypeEnum(String name) {
            for(Type type: Type.values()) {
                if (type.getType().equals(name)) {
                    return type;
                }
            }
            return APPLET;
        }
    }

    public static enum PropertyType {
        STAFF(1, "staff"),//员工
        AGENCY(2, "agency");//代理
        private Integer Type;
        private String Name;

        PropertyType(Integer Type, String Name) {
            this.Type = Type;
            this.Name = Name;
        }

        public Integer getType() {
            return this.Type;
        }
        public String getName() {
            return this.Name;
        }

        public static PropertyType getPaymentTypeEnum(String name) {
            for(PropertyType propertyType: PropertyType.values()) {
                if (propertyType.getName().equals(name)) {
                    return propertyType;
                }
            }
            return STAFF;
        }
    }

    public static enum IsPerpetual {
        EVER(0, "ever"),//员工
        NOTEVER(1, "notEver");//代理
        private Integer Type;
        private String Name;

        IsPerpetual(Integer Type, String Name) {
            this.Type = Type;
            this.Name = Name;
        }

        public Integer getType() {
            return this.Type;
        }
        public String getName() {
            return this.Name;
        }

        public static IsPerpetual getPaymentTypeEnum(String name) {
            for(IsPerpetual isPerpetual: IsPerpetual.values()) {
                if (isPerpetual.getName().equals(name)) {
                    return isPerpetual;
                }
            }
            return EVER;
        }
    }
}
