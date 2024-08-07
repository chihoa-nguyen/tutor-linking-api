package com.nchowf.tutorlinking.auth;

import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.enums.Role;
import com.nchowf.tutorlinking.enums.TokenType;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.parent.ParentService;
import com.nchowf.tutorlinking.parent.dto.ParentRequest;
import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import com.nchowf.tutorlinking.token.JwtService;
import com.nchowf.tutorlinking.token.RefreshTokenRequest;
import com.nchowf.tutorlinking.token.Token;
import com.nchowf.tutorlinking.token.TokenRepo;
import com.nchowf.tutorlinking.tutor.TutorService;
import com.nchowf.tutorlinking.tutor.dto.TutorDetailResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.user.User;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenRepo tokenRepo;
    private final JwtService jwtService;
    private final TutorService tutorService;
    private final ParentService parentService;
    private final PasswordEncoder passwordEncoder;
    private void saveToken(User user, String jwtId, TokenType type){
        Token token = Token.builder()
                .token(jwtId)
                .userId(user.getId())
                .type(type)
                .role(user.getRole())
                .build();
        tokenRepo.save(token);
    }
    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }
    public AuthResponse authenticate(AuthRequest request, String role) throws JOSEException, ParseException {
        User user = retrieveUserByEmailAndRole(request.getEmail(), role);
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_WRONG);
        }
        String jwtToken = jwtService.generateToken(user);
        String token = jwtService.extractJwtId(jwtToken);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveToken(user, token, TokenType.ACCESS);
        return new AuthResponse(jwtToken, refreshToken);
    }
    public Void sendEmail(String role, Integer id){
        if(role.equals("parent"))  parentService.sendVerificationEmail(id, role);
        else tutorService.sendVerificationEmail(id, role);
        return null;
    }
    public ParentResponse parentRegister(ParentRequest request){
        return parentService.register(request);
    }
    public TutorDetailResponse tutorRegister(TutorRequest request) throws IOException, ExecutionException, InterruptedException {
        return tutorService.register(request);
    }
    private User retrieveUserByEmailAndRole(String email, String role) {
        return (role.toUpperCase().equals(Role.TUTOR.toString())) ? tutorService.getTutorByEmail(email) : parentService.getParentByEmail(email);
    }
    public AuthResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        String userEmail = jwtService.extractUsername(request.getToken());
        User user = retrieveUserByEmailAndScope(userEmail, request.getToken());
        if (jwtService.isVerifiedToken(request.getToken()) && user != null) {
            String jwtToken = jwtService.generateToken(user);
            String token = jwtService.extractJwtId(jwtToken);
            revokeAllUserTokens(user);
            saveToken(user,token,TokenType.ACCESS);
            return new AuthResponse(jwtToken, request.getToken());
        }
        return null;
    }
    private User retrieveUserByEmailAndScope(String userEmail, String token) throws ParseException {
        String scope = jwtService.extractScope(token);
        return (scope.equals("PARENT")) ? parentService.getParentByEmail(userEmail) : tutorService.getTutorByEmail(userEmail);
    }
}
