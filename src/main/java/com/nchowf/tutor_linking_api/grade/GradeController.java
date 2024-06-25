package com.nchowf.tutor_linking_api.grade;

import com.nchowf.tutor_linking_api.grade.dto.GradeRequest;
import com.nchowf.tutor_linking_api.grade.dto.GradeResponse;
import com.nchowf.tutor_linking_api.subject.dto.SubjectRequest;
import com.nchowf.tutor_linking_api.subject.dto.SubjectResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/grade")
public class GradeController {
    private final GradeService gradeService;
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public GradeResponse create(@RequestBody @Valid GradeRequest request) {
        return gradeService.addGrade(request);
    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.FOUND)
    public List<GradeResponse> getGrades() {
        return gradeService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public GradeResponse getById(@PathVariable("id") Integer id) {
        return gradeService.getById(id);
    }
//    @GetMapping("/name/{name}")
//    @ResponseStatus(HttpStatus.FOUND)
//    public List<Subject> fetchSubjectByName(@PathVariable String name) {
//        return subjectService.getSubjectsByName(name);
//    }
    @PutMapping("/{id}")
    public GradeResponse update(@PathVariable("id") Integer id, @RequestBody @Valid GradeRequest request){
        return gradeService.updateGrade(id, request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        gradeService.delete(id);
    }
}
