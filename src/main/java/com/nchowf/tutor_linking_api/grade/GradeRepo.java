package com.nchowf.tutor_linking_api.grade;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepo extends JpaRepository<Grade, Integer> {
    boolean existsByName(String name);
}
