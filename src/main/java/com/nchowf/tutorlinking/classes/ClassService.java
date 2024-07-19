package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.classes.dto.ClassRequest;
import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.classes.dto.FilterClassRequest;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.grade.GradeService;
import com.nchowf.tutorlinking.parent.Parent;
import com.nchowf.tutorlinking.parent.ParentService;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.subject.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepo classRepo;
    private final GradeService gradeService;
    private final SubjectService subjectService;
    private final ParentService parentService;
    private final ClassMapper classMapper;

    public ClassResponse createClass(ClassRequest request) {
        Parent parent = parentService.getThisParent();
        List<Subject> subjects = subjectService.getAllById(request.getSubjects());
        Grade grade = gradeService.getById(request.getGradeId());
        Class classroom = classMapper.toClass(request);
        classroom.setParent(parent);
        classroom.setSubjects(new HashSet<>(subjects));
        classroom.setGrade(grade);
        return classMapper.toClassResponse(classRepo.save(classroom));
    }

    public List<ClassResponse> getAll() {
        return classRepo.findAllByHasTutorFalse().stream()
                .map(classMapper::toClassResponse).toList();
    }
    public Class getById(Integer id) {
        return classRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
    }
    public ClassResponse getResponseById(Class classroom) {
        return classMapper.toClassResponse(classroom);
    }
    public ClassResponse updateClass(Integer id, ClassRequest request) {
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
        return classMapper.toClassResponse(classRepo.save(classroom));
    }
    public void deleteClass(Integer id) {
        classRepo.deleteById(id);
    }

    public List<ClassResponse> getClasses(FilterClassRequest request) {
        return classRepo.findAll(request.toSpecification())
                .stream().map(classMapper::toClassResponse).toList();
    }
    public void updateHasTutor(Integer classId) {
        Class classroom = classRepo.findById(classId).orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
        classroom.setHasTutor(!classroom.isHasTutor());
        classRepo.save(classroom);
    }
}
