package com.nchowf.tutorlinking.classes.dto;

import com.nchowf.tutorlinking.classes.Class;
import com.nchowf.tutorlinking.classes.ClassSpecification;
import com.nchowf.tutorlinking.enums.Gender;
import com.nchowf.tutorlinking.enums.Position;
import com.nchowf.tutorlinking.utils.Filterable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Getter
@Setter
public class FilterClassRequest implements Filterable<Class> {
    private List<Integer> subjectIds;
    private List<Integer> gradeIds;
    private Gender gender;
    private List<Position> positions;
    private String city;
    private List<String> districts;
    @Override
    public Specification<Class> toSpecification(){
        Specification<Class> specification = Specification.where(null);
        if(!CollectionUtils.isEmpty(subjectIds)){
            specification = specification.and(ClassSpecification.hasSubjectIds(subjectIds.stream().toList()));
        }
        if(!CollectionUtils.isEmpty(gradeIds)){
            specification = specification.and(ClassSpecification.hasGradeIds(gradeIds.stream().toList()));
        }
        if(gender != null){
            specification = specification.and(ClassSpecification.hasGender(gender));
        }
        if(!CollectionUtils.isEmpty(positions)){
            specification = specification.and(ClassSpecification.hasPositions(positions.stream().toList()));
        }
        if(!city.isEmpty()){
            specification = specification.and(ClassSpecification.hasCity(city));
        }
        if(!CollectionUtils.isEmpty(districts)){
            specification = specification.and(ClassSpecification.hasDistricts(districts));
        }
        specification = specification.and(ClassSpecification.hasTutorFalse());
        return specification;
    }
}
