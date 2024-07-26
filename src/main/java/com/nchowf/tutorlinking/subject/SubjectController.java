package com.nchowf.tutorlinking.subject;

import com.nchowf.tutorlinking.subject.dto.SubjectRequest;
import com.nchowf.tutorlinking.subject.dto.SubjectResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/subject")
public class SubjectController {
    private final SubjectService subjectService;
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectResponse create(@RequestBody @Valid SubjectRequest subjectRequest) {
        return subjectService.create(subjectRequest);
    }

    @GetMapping("")
    //@ResponseStatus(HttpStatus.FOUND)
    public List<SubjectResponse> getSubjects() {
        return subjectService.getALl();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public SubjectResponse getById(@PathVariable("id") Integer id) {
        return subjectService.getById(id);
    }
    @PutMapping("/{id}")
    public SubjectResponse update(@PathVariable("id") Integer id, @RequestBody @Valid SubjectRequest request){
        return subjectService.updateSubject(id, request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        subjectService.delete(id);
    }
}
