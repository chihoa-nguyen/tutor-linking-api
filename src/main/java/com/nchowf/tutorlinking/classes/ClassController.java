package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.classes.dto.ClassRequest;
import com.nchowf.tutorlinking.classes.dto.ClassDetailResponse;
import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.classes.dto.FilterClassRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ClassDetailResponse create(@RequestBody @Valid ClassRequest request){
        return classService.createClass(request);
    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.FOUND)
    public List<ClassResponse> getClasses(@RequestBody FilterClassRequest request){
        return classService.getClasses(request);
    }
    @GetMapping("/parent")
    @ResponseStatus(HttpStatus.FOUND)
    public List<ClassDetailResponse> getClassesOfThisParent(){
        return classService.getClassesOfThisParent();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ClassResponse getById(@PathVariable("id") Integer id){
        return classService.getResponseById(classService.getById(id));
    }
    @GetMapping("/tutor")
    @ResponseStatus(HttpStatus.FOUND)
    public List<ClassResponse> getClassesSuitableForTutor(){
        return classService.getClassesSuitableForTutor();
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClassDetailResponse updateById(@PathVariable("id") Integer id, @RequestBody @Valid ClassRequest request){
        return classService.updateClass(id, request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        classService.deleteClass(id);
    }
}
