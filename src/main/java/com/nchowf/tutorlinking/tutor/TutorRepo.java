package com.nchowf.tutorlinking.tutor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TutorRepo extends JpaRepository<Tutor, Integer> {
    Optional<Tutor> findById(Integer id);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<Tutor> findByEmail(String email);
    @Query(value = "CALL GET_SUITABLE_TUTORS(?)", nativeQuery = true)
    List<Tutor> findTutorsSuitable(Integer classId);
}
