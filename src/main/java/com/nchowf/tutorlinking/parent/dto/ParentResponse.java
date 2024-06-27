package com.nchowf.tutorlinking.parent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentResponse {
    private Long parentId;
    private String name;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;
    private String password;
}
