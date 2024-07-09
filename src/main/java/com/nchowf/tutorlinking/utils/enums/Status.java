package com.nchowf.tutorlinking.utils.enums;

public enum Status {
    PENDING("Đang xem xét"),APPROVED("Được nhận lớp"), NO_APPROVED("Từ chối");
    private String value;
    Status(String value){
        this.value = value;
    }
    public String value(){
        return value;
    }
}
