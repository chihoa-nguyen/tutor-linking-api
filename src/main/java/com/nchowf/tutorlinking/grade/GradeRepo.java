package com.nchowf.tutorlinking.grade;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepo extends JpaRepository<Grade, Integer> {
    boolean existsByName(String name);
}
