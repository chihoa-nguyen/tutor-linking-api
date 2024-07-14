package com.nchowf.tutorlinking.user;

import com.nchowf.tutorlinking.auth.AuthRequest;
import com.nchowf.tutorlinking.auth.AuthResponse;
import com.nchowf.tutorlinking.user.dto.UserRequest;
import com.nchowf.tutorlinking.user.dto.UserResponse;
import com.nchowf.tutorlinking.user.dto.UserUpdateRequest;
import com.nimbusds.jose.JOSEException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService<T extends UserRequest, U extends UserUpdateRequest, R extends UserResponse> {
    R register(T request) throws IOException, ExecutionException, InterruptedException;
    AuthResponse authenticate(AuthRequest request) throws JOSEException;
    String verifyEmail(String token);
    R update(U request);
    R getById(Integer id);
    R getInforByToken();
    String getEmailFromToken();
    List<R> getAll();
    void delete(Integer id);
}
