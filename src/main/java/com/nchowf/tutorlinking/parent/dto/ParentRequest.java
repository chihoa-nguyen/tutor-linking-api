package com.nchowf.tutorlinking.parent.dto;

import com.nchowf.tutorlinking.user.dto.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentRequest extends UserRequest {
    private String address;
}
