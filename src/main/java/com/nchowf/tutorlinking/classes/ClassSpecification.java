package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.enums.Gender;
import com.nchowf.tutorlinking.enums.Position;
import com.nchowf.tutorlinking.subject.Subject;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Set;

@UtilityClass
public class ClassSpecification {
    public static Specification<Class> hasSubjectIds(Collection<Integer> subjectIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Class, Subject> subjectJoin = root.join("subjects");
            return subjectJoin.get("id").in(subjectIds);
        };
    }

    public static Specification<Class> hasGradeIds(Collection<Integer> gradeIds) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                root.get("grade").isNotNull(),
                root.get("grade").get("id").in(gradeIds)
        );
    }

    public static Specification<Class> hasGender(Gender gender) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("genderRequired"), gender);
            predicate = criteriaBuilder.or(predicate, criteriaBuilder.equal(root.get("genderRequired"), Gender.NONE));
            return predicate;
        };
    }

    //    public static Specification<Class> hasPositions(Collection<Position> positions) {
//        return (root, query, criteriaBuilder) -> {
//            Predicate predicate = null;
//            for (Position position : positions) {
//                Predicate currentPredicate = criteriaBuilder.equal(root.get("positionRequired"), position);
//                if (predicate == null) {
//                    predicate = currentPredicate;
//                } else {
//                    predicate = criteriaBuilder.or(predicate, currentPredicate);
//                }
//            }
//            return predicate;
//        };
//    }
    public static Specification<Class> hasPositions(Collection<Position> positions) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                root.get("positionRequired").isNotNull(),
                root.get("positionRequired").in(positions)
        );
    }

    public static Specification<Class> hasAddresses(Set<String> addresses) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = null;
            for (String address : addresses) {
                Predicate currentPredicate = criteriaBuilder.like(root.get("address"), "%" + address + "%");
                if (predicate == null) {
                    predicate = currentPredicate;
                } else {
                    predicate = criteriaBuilder.or(predicate, currentPredicate);
                }
            }
            return predicate;
        };
    }
}
