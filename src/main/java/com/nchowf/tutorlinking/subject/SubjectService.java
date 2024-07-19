package com.nchowf.tutorlinking.subject;

import com.nchowf.tutorlinking.exception.AppException;
import com.nchowf.tutorlinking.enums.ErrorCode;
import com.nchowf.tutorlinking.subject.dto.SubjectRequest;
import com.nchowf.tutorlinking.subject.dto.SubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final SubjectMapper subjectMapper;

    public SubjectResponse create(SubjectRequest request) {
        if(subjectRepo.existsSubjectByName(request.getName())){
            throw new AppException(ErrorCode.SUBJECT_EXISTED);
        }
        Subject subject = subjectMapper.toSubject(request);
        return subjectMapper.toSubjectResponse(subjectRepo.save(subject));
    }

    public List<SubjectResponse> getALl() {
        return subjectRepo.findAll().stream().map(subjectMapper::toSubjectResponse).toList();
    }
    public List<Subject> getAllById(Set<Integer> subjectIds){
        return subjectRepo.findAllById(subjectIds);
    }
    public SubjectResponse getById(Integer id) {
        Subject subject = subjectRepo.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.SUBJECT_NOT_FOUND));
        return subjectMapper.toSubjectResponse(subject);
    }

    public List<Subject> getByName(String name) {
        return subjectRepo.findSubjectsByNameContains(name);
    }
    public SubjectResponse updateSubject(Integer id, SubjectRequest request){
        Subject subject = subjectRepo.findById(id)
               .orElseThrow(()-> new AppException(ErrorCode.SUBJECT_NOT_FOUND));
        subject.setName(request.getName());
        return subjectMapper.toSubjectResponse(subjectRepo.save(subject));
    }
    public void delete(Integer id){
        subjectRepo.deleteById(id);
    }
}
