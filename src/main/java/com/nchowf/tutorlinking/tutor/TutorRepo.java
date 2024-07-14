package com.nchowf.tutorlinking.tutor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorRepo extends JpaRepository<Tutor, Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

    Optional<Tutor> findByEmailAndIsEnableTrue(String email);
}
