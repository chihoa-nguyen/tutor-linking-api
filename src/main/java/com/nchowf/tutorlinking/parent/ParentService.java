package com.nchowf.tutorlinking.parent;

import com.nchowf.tutorlinking.auth.AuthRequest;
import com.nchowf.tutorlinking.auth.AuthResponse;
import com.nchowf.tutorlinking.email.EmailService;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.enums.Role;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.parent.dto.ParentRequest;
import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import com.nchowf.tutorlinking.parent.dto.ParentUpdateRequest;
import com.nchowf.tutorlinking.token.JwtService;
import com.nchowf.tutorlinking.token.VerificationToken;
import com.nchowf.tutorlinking.token.VerificationTokenRepo;
import com.nchowf.tutorlinking.user.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService implements UserService<ParentRequest,ParentUpdateRequest,ParentResponse> {
    private final ParentRepo parentRepo;
    private final ParentMapper parentMapper;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final VerificationTokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ParentResponse register(ParentRequest request) {
        if (parentRepo.existsByPhoneNumber(request.getPhoneNumber()))
            throw new AppException(ErrorCode.PHONE_NUMBER_USED);
        if (parentRepo.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        Parent parent = parentMapper.toParent(request);
        parent.setRole(Role.PARENT);
        parent.setPassword(passwordEncoder.encode(request.getPassword()));
        parentRepo.save(parent);
        VerificationToken token = new VerificationToken(parent.getId(), Role.PARENT);
        tokenRepo.save(token);
        emailService.sendVerificationMail(parent.getName(),
                parent.getEmail(), token.getToken(),"parent");
        return parentMapper.toParentResponse(parentRepo
                .save(parent));
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) throws JOSEException {
        Parent parent = parentRepo.findByEmailAndIsEnableTrue(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));
        if (!passwordEncoder.matches(request.getPassword(), parent.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_WRONG);
        }
        String token = jwtService.generateToken(parent);
        return AuthResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }

    @Override
    public String verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepo.findByTokenAndRole(token, Role.PARENT);
        Parent parent = parentRepo.findById(verificationToken.getUserId())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        parent.setEnable(true);
        parentRepo.save(parent);
        return "<p>Địa chỉ email " + parent.getEmail() + " đã được xác minh !</p>";
    }

    @Override
    public ParentResponse getById(Integer id) {
        Parent parent = parentRepo.findById(id)
                .orElseThrow(() -> new AppException((ErrorCode.USER_NOT_EXISTED)));
        return parentMapper.toParentResponse(parent);
    }

    @Override
    public ParentResponse getInforByToken() {
        return parentMapper.toParentResponse(getThisParent());
    }
    public Parent getThisParent(){
        String email = getEmailFromToken();
        return parentRepo.findByEmailAndIsEnableTrue(email)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
    @Override
    public String getEmailFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public List<ParentResponse> getAll() {
        return parentRepo.findAllByIsEnableTrue().stream()
                .map(parentMapper::toParentResponse).toList();
    }
    @Override
    public ParentResponse update(ParentUpdateRequest parentRequest) {
        Parent parent = parentRepo.findByEmailAndIsEnableTrue(getEmailFromToken())
                .orElseThrow(() -> new AppException((ErrorCode.USER_NOT_EXISTED)));
        parentMapper.toUpdateParent(parent, parentRequest);
        return parentMapper.toParentResponse(parentRepo
               .save(parent));
    }
    @Override
    public void delete(Integer id) {
        Parent parent = parentRepo.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        parentRepo.delete(parent);
    }
}
