package com.nchowf.tutorlinking.subject;

import com.nchowf.tutorlinking.subject.dto.SubjectRequest;
import com.nchowf.tutorlinking.subject.dto.SubjectResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/subject")
public class SubjectController {
    private final SubjectService subjectService;
    @PostMapping("")
    public SubjectResponse create(@RequestBody @Valid SubjectRequest subjectRequest) {
        return subjectService.create(subjectRequest);
    }

    @GetMapping("")
    public List<SubjectResponse> getSubjects() {
        return subjectService.getALl();
    }

    @GetMapping("/{id}")
    public SubjectResponse getById(@PathVariable("id") Integer id) {
        return subjectService.getById(id);
    }
    @PutMapping("/{id}")
    public SubjectResponse update(@PathVariable("id") Integer id, @RequestBody @Valid SubjectRequest request){
        return subjectService.updateSubject(id, request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        subjectService.delete(id);
    }
}
