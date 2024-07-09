package com.nchowf.tutorlinking.user.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserResponse {
    private Integer id;
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
    public UserResponse(){}
    public UserResponse(Integer id, String email, String name, String phoneNumber, String address) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
