package com.nchowf.tutorlinking.parent;

import com.nchowf.tutorlinking.email.EmailService;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.enums.Role;
import com.nchowf.tutorlinking.enums.TokenType;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.parent.dto.ParentRequest;
import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import com.nchowf.tutorlinking.parent.dto.ParentUpdateRequest;
import com.nchowf.tutorlinking.token.Token;
import com.nchowf.tutorlinking.token.TokenRepo;
import com.nchowf.tutorlinking.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService implements UserService<ParentRequest,ParentUpdateRequest,ParentResponse> {
    private final ParentRepo parentRepo;
    private final TokenRepo tokenRepo;
    private final ParentMapper parentMapper;
    private final EmailService emailService;
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
        Token token = new Token(parent.getId(), Role.PARENT);
        tokenRepo.save(token);
        emailService.sendVerificationMail(parent.getName(),
                parent.getEmail(), token.getToken(),"parent");
        return parentMapper.toParentResponse(parentRepo
                .save(parent));
    }

    @Override
    public String verifyEmail(String token) {
        Token verificationToken = tokenRepo.findByTokenAndRoleAndAndType(token, Role.PARENT, TokenType.VERIFICATION);
        Parent parent = parentRepo.findById(verificationToken.getUserId())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        parent.setEnable(true);
        parentRepo.save(parent);
        return "<p>Địa chỉ email " + parent.getEmail() + " đã được xác minh !</p>";
    }


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

    public Parent getParentByEmail(String email) {
        return parentRepo.findByEmailAndIsEnableTrue(email)
               .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
