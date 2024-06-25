package com.nchowf.tutor_linking_api.subject;

import com.nchowf.tutor_linking_api.subject.dto.SubjectRequest;
import com.nchowf.tutor_linking_api.subject.dto.SubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final SubjectMapper subjectMapper;

    public SubjectResponse create(SubjectRequest subjectRequest) {
        Subject subject = subjectMapper.toSubject(subjectRequest);
//        if(subjectRepo.existsSubjectByName(subject.getName())){
//            throw new IllegalArgumentException("Subject already exists");
//        }
        return subjectMapper.toSubjectResponse(subjectRepo.save(subject));
    }

    public List<SubjectResponse> getALl() {
        return subjectRepo.findAll().stream().map(subjectMapper::toSubjectResponse).toList();
    }

    public SubjectResponse getById(Integer id) {
        Subject subject = subjectRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Subject not found"));

        return subjectMapper.toSubjectResponse(subject);
    }

    public List<Subject> getByName(String name) {
        return subjectRepo.findSubjectsByNameContains(name);
    }
    public SubjectResponse updateSubject(Integer id, SubjectRequest request){
        Subject subject = subjectRepo.findById(id)
               .orElseThrow(()-> new IllegalArgumentException("Subject not found"));
        subject.setName(request.getName());
        return subjectMapper.toSubjectResponse(subjectRepo.save(subject));
    }
    public void delete(Integer id){
        subjectRepo.deleteById(id);
    }
}
