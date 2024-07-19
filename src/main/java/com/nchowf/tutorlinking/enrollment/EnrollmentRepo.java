package com.nchowf.tutorlinking.enrollment;

import com.nchowf.tutorlinking.classes.Class;
import com.nchowf.tutorlinking.tutor.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepo extends JpaRepository<Enrollment, Integer> {
    boolean existsByTutorAndClassroom(Tutor tutor, Class classroom);
    Optional<Enrollment> findById(Integer id);
    List<Enrollment> findAllByClassroom(Class classroom);
    List<Enrollment> findAllByTutor(Tutor tutor);
    @Transactional
    @Modifying
    @Query(value = "UPDATE enrollment " +
                   "SET status = 'NO_APPROVED' " +
                   "WHERE class_id = ?1 AND tutor_id NOT IN (?2)", nativeQuery = true)
    void rejectOtherEnrollments(Integer classId, Integer tutorAcceptedId);
}
