package com.nchowf.tutorlinking.subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Integer> {
    List<Subject> findSubjectsByNameContains(String name);
    boolean existsSubjectByName(String name);

}
