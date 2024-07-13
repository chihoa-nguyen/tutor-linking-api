package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.auth.AuthRequest;
import com.nchowf.tutorlinking.auth.AuthResponse;
import com.nchowf.tutorlinking.email.EmailService;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.enums.Role;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.grade.GradeRepo;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.subject.SubjectRepo;
import com.nchowf.tutorlinking.token.JwtService;
import com.nchowf.tutorlinking.token.VerificationToken;
import com.nchowf.tutorlinking.token.VerificationTokenRepo;
import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorUpdateRequest;
import com.nchowf.tutorlinking.user.UserService;
import com.nchowf.tutorlinking.utils.UploadImgService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
public class TutorService implements UserService<TutorRequest, TutorUpdateRequest, TutorResponse> {
    private final TutorRepo tutorRepo;
    private final SubjectRepo subjectRepo;
    private final GradeRepo gradeRepo;
    private final VerificationTokenRepo tokenRepo;
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
    public TutorResponse register(TutorRequest request) throws IOException, ExecutionException, InterruptedException {
        if (tutorRepo.existsByPhoneNumber(request.getPhoneNumber()))
            throw new AppException(ErrorCode.PHONE_NUMBER_USED);
        if (tutorRepo.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        List<Subject> subjects = subjectRepo.findAllById(request.getSubjects());
        List<Grade> grades = gradeRepo.findAllById(request.getGrades());
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
        VerificationToken token = new VerificationToken(tutor.getId(), Role.TUTOR);
        tokenRepo.save(token);
        emailService.sendVerificationMail(tutor.getName(), tutor.getEmail(),
                token.getToken(), "tutor");
        return tutorMapper.tuTutorResponse(tutorRepo
                .save(tutor));
    }

    @Override
    public String verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepo.findByTokenAndRole(token, Role.TUTOR);
        Tutor tutor = tutorRepo.findById(verificationToken.getUserId())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        tutor.setEnable(true);
        tutorRepo.save(tutor);
        return "<h2>Tài khoản của bản đã được kích hoạt</h2>";
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) throws JOSEException {
        Tutor tutor = tutorRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), tutor.getPassword())) {
            throw new AppException(ErrorCode.PASSWORD_WRONG);
        }
        String token = jwtService.generateToken(tutor);
        return AuthResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
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
    public TutorResponse update(Integer id, TutorUpdateRequest request) {
        if (tutorRepo.existsByPhoneNumber(request.getPhoneNumber()))
            throw new AppException(ErrorCode.PHONE_NUMBER_USED);
        Tutor tutor = tutorRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Subject> subjects = subjectRepo.findAllById(request.getSubjects());
        List<Grade> grades = gradeRepo.findAllById(request.getGrades());
//        File[] files = prepareFileToUpload(request);
//        String[] url = uploadFileToDrive(files[0], files[1]);
        tutorMapper.updateTutor(tutor, request);
        tutor.setSubjects(new HashSet<>(subjects));
        tutor.setGrades(new HashSet<>(grades));
//        tutor.setAvt(url[0]);
//        tutor.setDegree(url[1]);
        return tutorMapper.tuTutorResponse(tutorRepo.save(tutor));
    }

    @Override
    public TutorResponse getById(Integer id) {
        Tutor tutor = tutorRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return tutorMapper.tuTutorResponse(tutor);
    }

    @Override
    public List<TutorResponse> getAll() {
        return tutorRepo.findAll().stream()
                .map(tutorMapper::tuTutorResponse).toList();
    }

    @Override
    public void delete(Integer id) {
        Tutor tutor = tutorRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        tutorRepo.delete(tutor);
    }
}
