package com.nchowf.tutorlinking.utils.enums;

public enum Gender {
    MAN("Nam"), FEMALE("Nữ");
    private String value;
    Gender(String value){
        this.value = value;
    }
    public String value(){
        return value;
    }
}
