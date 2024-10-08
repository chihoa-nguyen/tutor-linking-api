package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.classes.dto.ClassDetailResponse;
import com.nchowf.tutorlinking.classes.dto.ClassRequest;
import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.classes.dto.FilterClassRequest;
import com.nchowf.tutorlinking.email.EmailService;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.grade.GradeService;
import com.nchowf.tutorlinking.parent.Parent;
import com.nchowf.tutorlinking.parent.ParentService;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.subject.SubjectService;
import com.nchowf.tutorlinking.tutor.Tutor;
import com.nchowf.tutorlinking.tutor.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepo classRepo;
    private final GradeService gradeService;
    private final SubjectService subjectService;
    private final ParentService parentService;
    private final TutorService tutorService;
    private final EmailService mailService;
    private final ClassMapper classMapper;
    public ClassDetailResponse createClass(ClassRequest request) {
        Parent parent = parentService.getThisParent();
        List<Subject> subjects = subjectService.getAllById(request.getSubjects());
        Grade grade = gradeService.getById(request.getGradeId());
        Class classroom = classMapper.toClass(request);
        classroom.setParent(parent);
        classroom.setSubjects(new HashSet<>(subjects));
        classroom.setGrade(grade);
        return classMapper.toClassDetailResponse(classRepo.save(classroom));
    }
    public void sendClassToSuitableTutors(Integer classId){
        ClassResponse classroom = getResponseById(classId);
        List<Tutor> suitableTutors = tutorService.getTutorsSuitableForClass(classId);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        suitableTutors.forEach(tutor -> {
            executor.submit(() -> {
                mailService.sendClassSuitableMail(classroom, tutor);
            });
        });
        executor.shutdown();
    }
    public Class getById(Integer id) {
        return classRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
    }
    public ClassResponse getResponseById(Integer classId) {
        return classMapper.toClassResponse(getById(classId));
    }
    public ClassDetailResponse getDetailsById(Class classroom) {
        return classMapper.toClassDetailResponse(classroom);
    }
    public ClassDetailResponse updateClass(Integer id, ClassRequest request) {
        Class classroom = classRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
        Parent parent = parentService.getThisParent();
        if (!classroom.getParent().equals(parent)) {
            throw new AppException(ErrorCode.NOT_YOUR_CLASS);
        }
        if (!request.getGradeId().equals(classroom.getGrade().getId())) {
            Grade grade = gradeService.getById(request.getGradeId());
            classroom.setGrade(grade);
        }
        List<Subject> subjects = subjectService.getAllById(request.getSubjects());
        classMapper.updateClass(classroom, request);
        classroom.setSubjects(new HashSet<>(subjects));
        return classMapper.toClassDetailResponse(classRepo.save(classroom));
    }
    public void deleteClass(Integer id) {
        Class classroom = getById(id);
        if(classroom.isHasTutor()) throw new AppException(ErrorCode.CLASS_CANNOT_DELETE);
        classRepo.deleteById(id);
    }
    public List<ClassResponse> getClasses(FilterClassRequest request) {
        Sort sort = Sort.by("createdAt").descending();
        return classRepo.findAll(request.toSpecification(), sort)
                .stream().map(classMapper::toClassResponse).toList();
    }
    public void updateHasTutor(Integer classId) {
        Class classroom = classRepo.findById(classId).orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
        classroom.setHasTutor(true);
        classRepo.save(classroom);
    }
    public List<ClassDetailResponse> getClassesOfThisParent() {
        Parent parent = parentService.getThisParent();
        return classRepo.findAllByParent(parent).stream()
               .map(classMapper::toClassDetailResponse).toList();
    }
    public List<ClassResponse> getClassesSuitableForTutor(){
        Tutor tutor = tutorService.getThisTutor();
        return classRepo.getClassesSuitableForTutor(tutor.getId())
                .stream().map(classMapper::toClassResponse).toList();
    }
    public boolean isOwnerClass(Class classroom){
        Parent parent = parentService.getThisParent();
        return classroom.getParent().equals(parent);
    }
}
