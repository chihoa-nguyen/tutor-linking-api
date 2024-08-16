package com.nchowf.tutorlinking.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Integer> {
    boolean existsSubjectByName(String name);
}
