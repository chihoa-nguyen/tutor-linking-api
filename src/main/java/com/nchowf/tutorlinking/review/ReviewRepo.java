package com.nchowf.tutorlinking.review;

import com.nchowf.tutorlinking.enrollment.Enrollment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Integer> {
    boolean existsByEnrollment(Enrollment enrollment);
    @Query(value = " CALL GET_REVIEWS_OF_TUTOR(?)", nativeQuery = true)
    List<Review> findAllByTutorId(Integer id);
    @Modifying
    @Transactional
    @Query(value = " CALL UPDATE_AVG_RATING(?)", nativeQuery = true)
    void updateAvgRatingTutor(Integer id);
}

