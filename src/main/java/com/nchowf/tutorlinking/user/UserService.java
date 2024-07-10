package com.nchowf.tutorlinking.user;

import com.nchowf.tutorlinking.user.dto.UserRequest;
import com.nchowf.tutorlinking.user.dto.UserResponse;

import java.util.List;

public interface UserService<T extends UserRequest, R extends UserResponse> {
    R register(T request);
    R update(Integer id, T request);
    R getById(Integer id);
    List<R> getAll();
    void delete(Integer id);
}
