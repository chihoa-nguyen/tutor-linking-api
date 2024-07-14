package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.classes.dto.ClassRequest;
import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.grade.GradeRepo;
import com.nchowf.tutorlinking.parent.Parent;
import com.nchowf.tutorlinking.parent.ParentRepo;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.subject.SubjectRepo;
import com.nchowf.tutorlinking.enums.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassService {
    private final ClassRepo classRepo;
    private final ParentRepo parentRepo;
    private final ClassMapper classMapper;
    private final SubjectRepo subjectRepo;
    private final GradeRepo gradeRepo;
    public ClassResponse createClass(ClassRequest request){
        Parent parent = parentRepo.findById(request.getParentId())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<Subject> subjects = subjectRepo.findAllById(request.getSubjects());
        Grade grade = gradeRepo.findById(request.getGradeId())
                .orElseThrow(()-> new AppException(ErrorCode.GRADE_NOT_FOUND));
        Class classroom = classMapper.toClass(request);
        classroom.setParent(parent);
        classroom.setSubjects(new HashSet<>(subjects));
        classroom.setGrade(grade);
        return classMapper.toClassResponse(classRepo.save(classroom));
    }
    public List<ClassResponse> getAll(){
        return classRepo.findAll().stream()
               .map(classMapper::toClassResponse).toList();
    }
    public ClassResponse getById(Integer id){
        return classRepo.findById(id).map(classMapper::toClassResponse)
               .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
    }
    public ClassResponse updateClass(Integer id, ClassRequest request){
        Class classroom = classRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
        if(!request.getGradeId().equals(classroom.getGrade().getId())){
            Grade grade = gradeRepo.findById(request.getGradeId())
                    .orElseThrow(()-> new AppException(ErrorCode.GRADE_NOT_FOUND));
            classroom.setGrade(grade);
        }
        List<Subject> subjects = subjectRepo.findAllById(request.getSubjects());
        classMapper.updateClass(classroom, request);
        classroom.setSubjects(new HashSet<>(subjects));
        return classMapper.toClassResponse(classRepo.save(classroom));
    }
    public void deleteClass(Integer id){
        classRepo.deleteById(id);
    }
}
