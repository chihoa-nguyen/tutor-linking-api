package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.email.EmailService;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.enums.Role;
import com.nchowf.tutorlinking.enums.TokenType;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.grade.GradeService;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.subject.SubjectService;
import com.nchowf.tutorlinking.token.JwtService;
import com.nchowf.tutorlinking.token.Token;
import com.nchowf.tutorlinking.token.TokenRepo;
import com.nchowf.tutorlinking.tutor.dto.TutorDetailResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorUpdateRequest;
import com.nchowf.tutorlinking.user.UserService;
import com.nchowf.tutorlinking.utils.UploadImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class TutorService implements UserService<TutorRequest, TutorUpdateRequest, TutorDetailResponse> {
    private final TutorRepo tutorRepo;
    private final TokenRepo tokenRepo;
    private final SubjectService subjectService;
    private final GradeService gradeService;
    private final UploadImgService uploadImgService;
    private final EmailService emailService;
    private final TutorMapper tutorMapper;
    private final PasswordEncoder passwordEncoder;
    @Value("${gg-driver.avt-folder-id}")
    private String AVT_FOLDER_ID;
    @Value("${gg-driver.degree-folder-id}")
    private String DEGREE_FOLDER_ID;
    private final JwtService jwtService;
    @Override
    public TutorDetailResponse register(TutorRequest request) throws IOException, ExecutionException, InterruptedException {
        if (tutorRepo.existsByPhoneNumber(request.getPhoneNumber()))
            throw new AppException(ErrorCode.PHONE_NUMBER_USED);
        if (tutorRepo.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        List<Subject> subjects = subjectService.getAllById(request.getSubjects());
        List<Grade> grades = gradeService.getAllById(request.getGrades());
        File[] files = prepareFileToUpload(request);
        String[] url = uploadFileToDrive(files[0], files[1]);
        Tutor tutor = tutorMapper.toTutor(request);
        tutor.setRole(Role.TUTOR);
        tutor.setPassword(passwordEncoder.encode(request.getPassword()));
        tutor.setSubjects(new HashSet<>(subjects));
        tutor.setGrades(new HashSet<>(grades));
        tutor.setAvt(url[0]);
        tutor.setDegree(url[1]);
        tutorRepo.save(tutor);
        Token token = new Token(tutor.getId(), Role.TUTOR);
        tokenRepo.save(token);
        emailService.sendVerificationMail(tutor.getName(), tutor.getEmail(),
                token.getToken(), "tutor");
        return tutorMapper.tuTutorDetailResponse(tutorRepo
                .save(tutor));
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
    private File[] prepareFileToUpload(TutorRequest request) throws IOException {
        File tempAvtFile = File.createTempFile("avt_", null);
        File tempDegreeFile = File.createTempFile("degree_", null);
        request.getAvt().transferTo(tempAvtFile);
        request.getDegree().transferTo(tempDegreeFile);
        return new File[]{tempAvtFile, tempDegreeFile};
    }

    private String[] uploadFileToDrive(File tempImgFile, File tempEBookFile) throws ExecutionException, InterruptedException {
        CompletableFuture<String> avtUploadFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return uploadImgService.uploadToDrive(tempImgFile, AVT_FOLDER_ID);
            } catch (IOException | GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture<String> degreeUploadFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return uploadImgService.uploadToDrive(tempEBookFile, DEGREE_FOLDER_ID);
            } catch (IOException | GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        });
        String urlImg = avtUploadFuture.get();
        String urlEbook = degreeUploadFuture.get();
        tempImgFile.delete();
        tempEBookFile.delete();
        return new String[]{urlImg, urlEbook};
    }

    @Override
    public TutorDetailResponse update(TutorUpdateRequest request) {
        if (tutorRepo.existsByPhoneNumber(request.getPhoneNumber()))
            throw new AppException(ErrorCode.PHONE_NUMBER_USED);
        Tutor tutor = tutorRepo.findByEmailAndIsEnableTrue(getEmailFromToken())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Subject> subjects = subjectService.getAllById(request.getSubjects());
        List<Grade> grades = gradeService.getAllById(request.getGrades());
//        File[] files = prepareFileToUpload(request);
//        String[] url = uploadFileToDrive(files[0], files[1]);
        tutorMapper.updateTutor(tutor, request);
        tutor.setSubjects(new HashSet<>(subjects));
        tutor.setGrades(new HashSet<>(grades));
//        tutor.setAvt(url[0]);
//        tutor.setDegree(url[1]);
        return tutorMapper.tuTutorDetailResponse(tutorRepo.save(tutor));
    }


    public TutorResponse getById(Integer id){
        Tutor tutor = tutorRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return tutorMapper.tuTutorResponse(tutor);
    }

    public Tutor getThisTutor(){
        String email = getEmailFromToken();
        return tutorRepo.findByEmailAndIsEnableTrue(email)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
    @Override
    public TutorDetailResponse getInforByToken() {
        String email = getEmailFromToken();
        Tutor tutor = tutorRepo.findByEmailAndIsEnableTrue(email).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        return tutorMapper.tuTutorDetailResponse(tutor);
    }

    @Override
    public String getEmailFromToken() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<TutorResponse> getAll() {
        return tutorRepo.findAll().stream()
                .map(tutorMapper::tuTutorResponse).toList();
    }
    public List<TutorResponse> getTutorsSuitableForClass(Integer classId) {
        return tutorRepo.findTutorsSuitable(classId)
                .stream().map(tutorMapper::tuTutorResponse).toList();
    }
    @Override
    public void delete(Integer id) {
        Tutor tutor = tutorRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        tutorRepo.delete(tutor);
    }

    public Tutor getTutorByEmail(String email) {
        return tutorRepo.findByEmailAndIsEnableTrue(email)
               .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
