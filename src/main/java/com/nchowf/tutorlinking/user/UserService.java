package com.nchowf.tutorlinking.user;

import com.nchowf.tutorlinking.auth.dto.ChangePasswordRequest;
import com.nchowf.tutorlinking.user.dto.UserRequest;
import com.nchowf.tutorlinking.user.dto.UserResponse;
import com.nchowf.tutorlinking.user.dto.UserUpdateRequest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface UserService<T extends UserRequest, U extends UserUpdateRequest, R extends UserResponse> {
    R register(T request) throws IOException, ExecutionException, InterruptedException;
    void sendVerificationEmail(Integer id, String role);
    void forgotPassword(String email);
    String verifyEmail(String token);
    R update(U request);
    R getInforByToken();
    String getEmailFromToken();
    void changePassword(ChangePasswordRequest request);
    void delete(Integer id);
}
