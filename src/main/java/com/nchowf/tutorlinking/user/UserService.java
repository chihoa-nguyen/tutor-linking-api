package com.nchowf.tutorlinking.user;

import com.nchowf.tutorlinking.user.dto.UserRequest;
import com.nchowf.tutorlinking.user.dto.UserResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService<T extends UserRequest, R extends UserResponse> {
    R register(T request) throws IOException, ExecutionException, InterruptedException;
    R update(Integer id, T request);
    R getById(Integer id);
    List<R> getAll();
    void delete(Integer id);
}
