package com.nchowf.tutorlinking.enrollment;

import com.nchowf.tutorlinking.classes.Class;
import com.nchowf.tutorlinking.classes.ClassService;
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
    private final ClassService classService;
    private final EnrollmentMapper enrollmentMapper;
    public EnrollmentResponse createEnrollment(Integer classId) {
        Tutor tutor = tutorService.getThisTutor();
        Class classroom = classService.getById(classId);
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
        Class classroom = classService.getById(classId);
        if(!classService.isOwnerClass(classroom)) throw new AppException(ErrorCode.NOT_YOUR_CLASS);
        List<Enrollment> enrollments = enrollmentRepo.findAllByClassroom(classroom);
        return enrollments.stream()
                .map(enrollmentMapper::toEnrollmentResponse).toList();
    }
    public Void acceptEnrollment(Integer id) {
        Enrollment enrollment = enrollmentRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND));
        enrollment.setStatus(Status.APPROVED);
        classService.updateHasTutor(enrollment.getClassroom().getId());
        enrollmentRepo.rejectOtherEnrollments(enrollment.getClassroom().getId(), enrollment.getTutor().getId());
        enrollmentRepo.save(enrollment);
        return null;
    }
    public Void sendAcceptMail(Integer id){
        Enrollment enrollment = enrollmentRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENROLLMENT_NOT_FOUND));

        emailService.sendClassDetailsMail(enrollment, enrollment.getTutor().getEmail());
        return null;
    }
    public List<EnrollmentResponse> getEnrollmentsOfTutor(){
        Tutor tutor = tutorService.getThisTutor();
        List<Enrollment> enrollments = enrollmentRepo.findAllByTutor(tutor);
        return enrollments.stream()
               .map(enrollmentMapper::toEnrollmentResponse).toList();
    }
    public List<EnrollmentResponse> getClassTeachingOfTutor(Integer id){
        Tutor tutor = tutorService.getTutor(id);
        List<Enrollment> enrollments = enrollmentRepo.findAllByTutor(tutor);
        return enrollments.stream()
                .filter(e -> e.getStatus() == Status.APPROVED)
                .map(enrollmentMapper::toEnrollmentResponse)
                .toList();
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
    public Enrollment findById(Integer enrollmentId) {
        return enrollmentRepo.findById(enrollmentId)
                .orElseThrow(()->new AppException(ErrorCode.ENROLLMENT_NOT_FOUND));
    }
    public EnrollmentResponse getEnrollmentResponse(Enrollment enrollment){
        return enrollmentMapper.toEnrollmentResponse(enrollment);
    }
}