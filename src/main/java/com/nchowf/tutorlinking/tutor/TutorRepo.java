package com.nchowf.tutorlinking.tutor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepo extends JpaRepository<Tutor, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
}