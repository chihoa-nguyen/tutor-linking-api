package com.nchowf.tutorlinking.enums;

public enum Gender {
    MALE("Nam"), FEMALE("Nữ"), NONE("Không yêu cầu");
    private final String value;
    Gender(String value){
        this.value = value;
    }
    public String value(){
        return value;
    }
}
