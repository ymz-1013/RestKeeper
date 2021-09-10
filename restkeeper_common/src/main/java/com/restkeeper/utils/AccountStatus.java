package com.restkeeper.utils;


/**
 * 账号状态
 */
public enum AccountStatus {
    Trial(0,"试用账号"),
    Official(1,"正式账号"),
    Forbidden(-1,"禁用账号");

    private  int status;
    private  String  desc;

    AccountStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
