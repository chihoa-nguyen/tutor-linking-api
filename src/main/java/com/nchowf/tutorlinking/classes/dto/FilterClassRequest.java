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

import java.util.Set;
@Getter
@Setter
public class FilterClassRequest implements Filterable<Class> {
    private Filter filter;
    @Getter
    @Setter
    private static class Filter{
        private Set<Integer> subjectIds;
        private Set<Integer> gradeIds;
        private Gender  gender;
        private Set<Position> positions;
        private Set<String> addresses;
    }
    @Override
    public Specification<Class> toSpecification(){
        Specification<Class> specification = Specification.where(null);
        if(!CollectionUtils.isEmpty(filter.subjectIds)){
            specification = specification.and(ClassSpecification.hasSubjectIds(filter.subjectIds.stream().toList()));
        }
        if(!CollectionUtils.isEmpty(filter.gradeIds)){
            specification = specification.and(ClassSpecification.hasGradeIds(filter.gradeIds.stream().toList()));
        }
        if(filter.gender != null){
            specification = specification.and(ClassSpecification.hasGender(filter.gender));
        }
        if(!CollectionUtils.isEmpty(filter.positions)){
            specification = specification.and(ClassSpecification.hasPositions(filter.positions.stream().toList()));
        }
        if(!CollectionUtils.isEmpty(filter.addresses)){
            specification = specification.and(ClassSpecification.hasAddresses(filter.addresses));
        }
        return specification;
    }
}
