package com.nchowf.tutorlinking.user.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class UserResponse {
    private Integer id;
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
    private Date createdAt;
    private Date updatedAt;
    public UserResponse(){}
    public UserResponse(Integer id, String email, String name, String phoneNumber, String address, Date createdAt, Date updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
