package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.auth.ChangePasswordRequest;
import com.nchowf.tutorlinking.email.EmailService;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.enums.Role;
import com.nchowf.tutorlinking.enums.TokenType;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.grade.GradeService;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.subject.SubjectService;
import com.nchowf.tutorlinking.token.Token;
import com.nchowf.tutorlinking.token.TokenRepo;
import com.nchowf.tutorlinking.tutor.dto.TutorDetailResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorUpdateRequest;
import com.nchowf.tutorlinking.user.UserService;
import com.nchowf.tutorlinking.utils.CloudinaryService;
import com.nchowf.tutorlinking.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TutorService implements UserService<TutorRequest, TutorUpdateRequest, TutorDetailResponse> {
    private final TutorRepo tutorRepo;
    private final TokenRepo tokenRepo;
    private final SubjectService subjectService;
    private final GradeService gradeService;
    private final EmailService emailService;
    private final CloudinaryService cloudinaryService;
    private final TutorMapper tutorMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public TutorDetailResponse register(TutorRequest request){
        if (tutorRepo.existsByPhoneNumber(request.getPhoneNumber()))
            throw new AppException(ErrorCode.PHONE_NUMBER_USED);
        if (tutorRepo.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        List<Subject> subjects = subjectService.getAllById(request.getSubjects());
        List<Grade> grades = gradeService.getAllById(request.getGrades());
        Tutor tutor = tutorMapper.toTutor(request);
        tutor.setRole(Role.TUTOR);
        tutor.setPassword(passwordEncoder.encode(request.getPassword()));
        tutor.setSubjects(new HashSet<>(subjects));
        tutor.setGrades(new HashSet<>(grades));
        return tutorMapper.tuTutorDetailResponse(tutorRepo
                .save(tutor));
    }
    @Transactional
    public void uploadAndUpdateTutorImage(Integer tutor_id, MultipartFile avt, MultipartFile degree) {
        Tutor tutor = tutorRepo.findById(tutor_id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        FileUploadUtil.assertAllowed(avt, FileUploadUtil.IMAGE_PATTERN);
        FileUploadUtil.assertAllowed(degree, FileUploadUtil.IMAGE_PATTERN);
        CompletableFuture<String> avtUploadFuture = CompletableFuture.supplyAsync(() -> {
            String fileName = FileUploadUtil.getFileName(avt.getOriginalFilename());
            return this.cloudinaryService.uploadFile(avt, fileName);
        });
        CompletableFuture<String> degreeUploadFuture = CompletableFuture.supplyAsync(() -> {
            String fileName = FileUploadUtil.getFileName(degree.getOriginalFilename());
            return this.cloudinaryService.uploadFile(degree, fileName);
        });
        String avtUrl = avtUploadFuture.join();
        String degreeUrl = degreeUploadFuture.join();
        tutor.setAvt(avtUrl);
        tutor.setDegree(degreeUrl);
        tutorRepo.save(tutor);
    }
    @Override
    public void sendVerificationEmail(Integer id, String role) {
        Tutor tutor = tutorRepo.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        Token token = new Token(id, Role.TUTOR);
        tokenRepo.save(token);
        emailService.sendVerificationMail(tutor.getName(), tutor.getEmail(),
                token.getToken(), role);
    }
    @Override
    public String verifyEmail(String token) {
        Token verificationToken = tokenRepo.findByTokenAndRoleAndAndType(token, Role.TUTOR, TokenType.VERIFICATION);
        Tutor tutor = tutorRepo.findById(verificationToken.getUserId())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        tutor.setEnable(true);
        tutorRepo.save(tutor);
        return "<p>Địa chỉ email " + tutor.getEmail()+" đã được xác minh</p>";
    }

    @Override
    public TutorDetailResponse update(TutorUpdateRequest request) {
        if (tutorRepo.existsByPhoneNumber(request.getPhoneNumber()))
            throw new AppException(ErrorCode.PHONE_NUMBER_USED);
        Tutor tutor = tutorRepo.findByEmail(getEmailFromToken())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Subject> subjects = subjectService.getAllById(request.getSubjects());
        List<Grade> grades = gradeService.getAllById(request.getGrades());
        tutorMapper.updateTutor(tutor, request);
        tutor.setSubjects(new HashSet<>(subjects));
        tutor.setGrades(new HashSet<>(grades));
        return tutorMapper.tuTutorDetailResponse(tutorRepo.save(tutor));
    }
    public TutorResponse getById(Integer id){
        return tutorMapper.tuTutorResponse(getTutor(id));
    }
    public Tutor getTutor(Integer id){
        return  tutorRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
    public Tutor getThisTutor(){
        String email = getEmailFromToken();
        return tutorRepo.findByEmail(email)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
    @Override
    public TutorDetailResponse getInforByToken() {
        String email = getEmailFromToken();
        Tutor tutor = tutorRepo.findByEmail(email).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        return tutorMapper.tuTutorDetailResponse(tutor);
    }

    @Override
    public String getEmailFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        Tutor tutor = tutorRepo.findByEmail(request.getEmail()).orElseThrow(() ->new AppException(ErrorCode.USER_NOT_EXISTED));
        if (!passwordEncoder.matches(request.getOldPassword(), tutor.getPassword())) throw  new AppException(ErrorCode.PASSWORD_WRONG);
        tutor.setPassword(passwordEncoder.encode(request.getNewPassword()));
        tutorRepo.save(tutor);
    }

    public List<TutorResponse> getAll() {
        return tutorRepo.findAll().stream()
                .map(tutorMapper::tuTutorResponse).toList();
    }
    public List<Tutor> getTutorsSuitableForClass(Integer classId) {
        return tutorRepo.findTutorsSuitable(classId);
    }
    @Override
    public void delete(Integer id) {
        Tutor tutor = tutorRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        tutorRepo.delete(tutor);
    }
    public Tutor getTutorByEmail(String email) {
        Tutor tutor = tutorRepo.findByEmail(email)
               .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if(!tutor.isEnable()) throw new AppException(ErrorCode.USER_NOT_ENABLED);
        return tutor;
    }
}
