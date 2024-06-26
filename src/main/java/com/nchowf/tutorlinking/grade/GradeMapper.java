package com.nchowf.tutorlinking.grade;

import com.nchowf.tutorlinking.grade.dto.GradeRequest;
import com.nchowf.tutorlinking.grade.dto.GradeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    Grade toGrade (GradeRequest request);
    GradeResponse toGradeResponse(Grade grade);
}
