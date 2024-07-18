package com.nchowf.tutorlinking.enrollment;

import com.nchowf.tutorlinking.classes.Class;
import com.nchowf.tutorlinking.classes.ClassRepo;
import com.nchowf.tutorlinking.email.EmailService;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.enums.Status;
import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.tutor.Tutor;
import com.nchowf.tutorlinking.tutor.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final TutorService tutorService;
    private final EmailService emailService;
    private final EnrollmentRepo enrollmentRepo;
    private final ClassRepo classRepo;
    private final EnrollmentMapper enrollmentMapper;
    public EnrollmentResponse createEnrollment(Integer classId) {
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
        return enrollmentMapper.toEnrollmentResponse(enrollmentRepo.save(enrollment));
    }
    public List<EnrollmentResponse> getEnrollmentsOfClass(Integer classId){
        Class classroom = classRepo.findById(classId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASS_NOT_FOUND));
        List<Enrollment> enrollments = enrollmentRepo.findAllByClassroom(classroom);
        return enrollments.stream()
                .map(enrollmentMapper::toEnrollmentResponse).toList();
    }
    public EnrollmentResponse acceptEnrollment(Integer id) {
        Enrollment enrollment = enrollmentRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND));
        enrollment.setStatus(Status.APPROVED);
        emailService.sendClassDetailsMail(enrollment, enrollment.getTutor().getEmail());
        enrollmentRepo.rejectOtherEnrollments(enrollment.getId(), enrollment.getTutor().getId());
        return enrollmentMapper.toEnrollmentResponse(enrollmentRepo.save(enrollment));
    }
    public List<EnrollmentResponse> getEnrollmentsOfTutor(){
        Tutor tutor = tutorService.getThisTutor();
        List<Enrollment> enrollments = enrollmentRepo.findAllByTutor(tutor);
        return enrollments.stream()
               .map(enrollmentMapper::toEnrollmentResponse).toList();
    }
    public void deleteEnrollment(Integer id) {
        Enrollment enrollment = enrollmentRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND));
        Tutor tutor = tutorService.getThisTutor();
        if (!enrollment.getTutor().equals(tutor)) {
            throw new AppException(ErrorCode.NOT_YOUR_ENROLLMENT);
        }
        enrollmentRepo.delete(enrollment);
    }
}