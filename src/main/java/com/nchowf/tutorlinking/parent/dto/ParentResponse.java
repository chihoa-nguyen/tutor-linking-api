package com.nchowf.tutorlinking.parent.dto;

import com.nchowf.tutorlinking.user.dto.UserResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ParentResponse extends UserResponse {
    public ParentResponse(Integer id, String email,String name, String phoneNumber, String address){
        super(id, email, name, phoneNumber, address);
    }
    public ParentResponse() {
        super();
    }
}
