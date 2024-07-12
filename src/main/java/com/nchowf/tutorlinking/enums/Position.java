package com.nchowf.tutorlinking.enums;

public enum Position {
    STUDENT("Sinh viên"),TEACHER("Giáo viên"), GRADUATED_STUDENT("Sinh viên tốt nghiệp");
    private String value;
    Position(String value){
        this.value = value;
    }
    public String value(){
        return value;
    }
}
