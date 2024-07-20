package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.enums.Gender;
import com.nchowf.tutorlinking.enums.Position;
import com.nchowf.tutorlinking.subject.Subject;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

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

    public static Specification<Class> hasPositions(Collection<Position> positions) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                root.get("positionRequired").isNotNull(),
                root.get("positionRequired").in(positions)
        );
    }

    public static Specification<Class> hasDistricts(Collection<String> districts) {
        return (root, query, criteriaBuilder) -> {
            Predicate districtPredicate =
                    criteriaBuilder.function("JSON_VALUE", String.class,
                            root.get("address"),
                            criteriaBuilder.literal("$.district")).in(districts);
            return districtPredicate;
        };
    }

    public static Specification<Class> hasCity(String city) {
        return (root, query, criteriaBuilder) -> {
            Predicate cityPredicate = criteriaBuilder.equal(
                    criteriaBuilder.function("JSON_VALUE", String.class, root.get("address"), criteriaBuilder.literal("$.city")),
                    criteriaBuilder.literal(city)
            );
            return cityPredicate;
        };
    }

    public static Specification<Class> hasTutorFalse() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("hasTutor"), Boolean.FALSE);
    }
}
