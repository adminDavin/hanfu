package com.hanfu.user.center.utils;


public enum UserStatus {
    AUDITING(1, "auditing"), UNAUDIT(0, "unaudit"), UPDATEEDUSERMESSAGE(6, "updatedStatus"), AGREE(2, "agree"),
    REJECT(3, "reject"), MANAGER(5, "manager");

    private int value;
    private String status;

    public int getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

    // 普通方法
    public static int getName(String status) {
        for (UserStatus c : UserStatus.values()) {
            if (c.getStatus().equals(status)) {
                return c.getValue();
            }
        }
        return -1;
    }

    // 普通方法
    public static UserStatus getName(int value) {
        for (UserStatus c : UserStatus.values()) {
            if (c.getValue() == value) {
                return c;
            }
        }
        return null;
    }

    UserStatus(int value, String status) {
        this.value = value;
        this.status = status;
    }

    // 普通方法
    public static int auditStatus(String status) {
        for (UserStatus c : UserStatus.values()) {
            if (c.value >= 5) {
                continue;
            }
            if (c.getStatus().equals(status)) {
                return c.getValue();
            }
        }
        return -1;
    }


}
