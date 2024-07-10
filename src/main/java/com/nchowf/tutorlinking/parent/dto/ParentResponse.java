package com.nchowf.tutorlinking.parent.dto;

import com.nchowf.tutorlinking.user.dto.UserResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
public class ParentResponse extends UserResponse {
    public ParentResponse(Integer id, String email, String name, String phoneNumber, String address, Date createdAt, Date updatedAt) {
        super(id, email, name, phoneNumber, address, createdAt, updatedAt);
    }
    public ParentResponse() {
        super();
    }
}
