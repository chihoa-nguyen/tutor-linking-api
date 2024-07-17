package com.nchowf.tutorlinking.enrollment;

import com.nchowf.tutorlinking.classes.Class;
import com.nchowf.tutorlinking.classes.ClassRepo;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.enums.Status;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.tutor.Tutor;
import com.nchowf.tutorlinking.tutor.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepo enrollmentRepo;
    private final TutorService tutorService;
    private final ClassRepo classRepo;
    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentResponse createRegistration(Integer classId) {
        Tutor tutor = tutorService.getThisTutor();
        Class classroom = classRepo.findById(classId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
        if (enrollmentRepo.existsByTutorAndClassroom(tutor, classroom)) {
            throw new AppException(ErrorCode.ALREADY_REGISTERED);
        }
        Enrollment enrollment = Enrollment.builder()
                .classroom(classroom)
                .tutor(tutor)
                .status(Status.PENDING)
                .build();
        return enrollmentMapper.toRegistrationResponse(enrollmentRepo.save(enrollment));
    }

    //        public Registration updateRegistration(Registration registration) throws RegistrationException {
//            // Validation (consider security and data integrity)
//            validateRegistrationUpdate(registration);
//
//            Registration existingRegistration = registrationRepository.findById(registration.getId())
//                    .orElseThrow(() -> new RegistrationException("Registration not found"));
//
//            // Update fields (be selective to avoid unintended changes)
//            existingRegistration.setStatus(registration.getStatus());  // Example update
//
//            return registrationRepository.save(existingRegistration);
//        }
//
    public void deleteRegistration(Integer id) {
        Enrollment enrollment = enrollmentRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REGISTRATION_NOT_FOUND));
        Tutor tutor = tutorService.getThisTutor();
        if (!enrollment.getTutor().equals(tutor)) {
            throw new AppException(ErrorCode.NOT_YOUR_REGISTRATION);
        }
        enrollmentRepo.delete(enrollment);
    }
}

