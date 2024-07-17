package com.nchowf.tutorlinking.class_registration;

import com.nchowf.tutorlinking.classes.Class;
import com.nchowf.tutorlinking.tutor.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepo extends JpaRepository<Registration, Integer> {
    boolean existsByTutorAndClassroom(Tutor tutor, Class classroom);
}
