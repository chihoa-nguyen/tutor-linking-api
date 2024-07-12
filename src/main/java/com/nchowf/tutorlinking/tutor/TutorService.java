package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.grade.GradeRepo;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.subject.SubjectRepo;
import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import com.nchowf.tutorlinking.tutor.dto.TutorUpdateRequest;
import com.nchowf.tutorlinking.user.UserService;
import com.nchowf.tutorlinking.utils.UploadImgService;
import com.nchowf.tutorlinking.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final UploadImgService uploadImgService;
    private final TutorMapper tutorMapper;
    @Value("${gg-driver.avt-folder-id}")
    private String AVT_FOLDER_ID;
    @Value("${gg-driver.degree-folder-id}")
    private String DEGREE_FOLDER_ID;
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
        tutor.setSubjects(new HashSet<>(subjects));
        tutor.setGrades(new HashSet<>(grades));
        tutor.setAvt(url[0]);
        tutor.setDegree(url[1]);
        return tutorMapper.tuTutorResponse(tutorRepo.save(tutor));
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
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
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
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        tutorRepo.delete(tutor);
    }
}
