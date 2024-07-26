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
}
