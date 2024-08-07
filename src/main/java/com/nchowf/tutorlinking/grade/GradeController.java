package com.nchowf.tutorlinking.grade;

import com.nchowf.tutorlinking.grade.dto.GradeRequest;
import com.nchowf.tutorlinking.grade.dto.GradeResponse;
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
    public GradeResponse create(@RequestBody @Valid GradeRequest request) {
        return gradeService.addGrade(request);
    }

    @GetMapping("")
    public List<GradeResponse> getGrades() {
        return gradeService.getAll();
    }

    @GetMapping("/{id}")
    public GradeResponse getById(@PathVariable("id") Integer id) {
        return gradeService.getResponseById(gradeService.getById(id));
    }

    @PutMapping("/{id}")
    public GradeResponse update(@PathVariable("id") Integer id, @RequestBody @Valid GradeRequest request) {
        return gradeService.updateGrade(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        gradeService.delete(id);
    }
}
