package com.nchowf.tutorlinking.parent.dto;

import com.nchowf.tutorlinking.user.dto.UserResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data()
@SuperBuilder
public class ParentResponse extends UserResponse {
}
