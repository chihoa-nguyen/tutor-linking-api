package com.nchowf.tutorlinking.user;

import com.nchowf.tutorlinking.user.dto.UserRequest;
import com.nchowf.tutorlinking.user.dto.UserResponse;

public interface UserService<T extends UserRequest, R extends UserResponse> {
    R register(T request);
    
}
