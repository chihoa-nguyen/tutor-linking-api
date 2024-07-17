package com.nchowf.tutorlinking.class_registration;

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
public class RegistrationService {
    private final RegistrationRepo registrationRepo;
    private final TutorService tutorService;
    private final ClassRepo classRepo;
    private final RegistrationMapper registrationMapper;

    public RegistrationResponse createRegistration(Integer classId) {
        Tutor tutor = tutorService.getThisTutor();
        Class classroom = classRepo.findById(classId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
        if (registrationRepo.existsByTutorAndClassroom(tutor, classroom)) {
            throw new AppException(ErrorCode.ALREADY_REGISTERED);
        }
        Registration registration = Registration.builder()
                .classroom(classroom)
                .tutor(tutor)
                .status(Status.PENDING)
                .build();
        return registrationMapper.toRegistrationResponse(registrationRepo.save(registration));
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
        Registration registration = registrationRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REGISTRATION_NOT_FOUND));
        Tutor tutor = tutorService.getThisTutor();
        if (!registration.getTutor().equals(tutor)) {
            throw new AppException(ErrorCode.NOT_YOUR_REGISTRATION);
        }
        registrationRepo.delete(registration);
    }
}

