package com.nchowf.tutorlinking.review;

import com.nchowf.tutorlinking.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Integer> {
    boolean existsByEnrollment(Enrollment enrollment);
    @Query(value = " CALL GET_REVIEWS_OF_TUTOR(?)", nativeQuery = true)
    List<Review> findAllByTutorId(Integer id);
}

