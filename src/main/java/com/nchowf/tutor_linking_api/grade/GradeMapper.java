package com.nchowf.tutor_linking_api.grade;

import com.nchowf.tutor_linking_api.grade.dto.GradeRequest;
import com.nchowf.tutor_linking_api.grade.dto.GradeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    Grade toGrade (GradeRequest request);
    GradeResponse toGradeResponse(Grade grade);
}
