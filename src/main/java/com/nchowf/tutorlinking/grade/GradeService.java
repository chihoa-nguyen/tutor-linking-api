package com.nchowf.tutorlinking.grade;

import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.grade.dto.GradeRequest;
import com.nchowf.tutorlinking.grade.dto.GradeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepo gradeRepo;
    private final GradeMapper gradeMapper;
    public GradeResponse addGrade(GradeRequest request){
        if(gradeRepo.existsByName(request.getName())){
            throw new AppException(ErrorCode.GRADE_EXISTED);
        }
        Grade grade = gradeMapper.toGrade(request);
        return gradeMapper.toGradeResponse(gradeRepo.save(grade));
    }
    public List<GradeResponse> getAll(){
        return gradeRepo.findAll().stream()
                .map(gradeMapper::toGradeResponse).toList();
    }
    public List<Grade> getAllById(Set<Integer> gradeIds){
        return gradeRepo.findAllById(gradeIds);
    }
    public Grade getById(Integer id){
        return gradeRepo.findById(id)
               .orElseThrow(() -> new AppException(ErrorCode.GRADE_NOT_FOUND));
    }
    public GradeResponse getResponseById(Grade grade){
        return gradeMapper.toGradeResponse(grade);
    }
    public GradeResponse updateGrade(Integer id, GradeRequest request){
        Grade grade = gradeRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.GRADE_NOT_FOUND));
        grade.setName(request.getName());
        return gradeMapper.toGradeResponse(gradeRepo.save(grade));
    }
    public void delete(Integer id){
        gradeRepo.deleteById(id);
    }
}
