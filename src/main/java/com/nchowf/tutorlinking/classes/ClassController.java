package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.classes.dto.ClassRequest;
import com.nchowf.tutorlinking.classes.dto.ClassResponse;
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
    public ClassResponse create(@RequestBody @Valid ClassRequest request){
        return classService.createClass(request);
    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.FOUND)
    public List<ClassResponse> getAll(){
        return classService.getAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ClassResponse getById(@PathVariable("id") Integer id){
        return classService.getById(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClassResponse updateById(@PathVariable("id") Integer id, @RequestBody @Valid ClassRequest request){
        return classService.updateClass(id, request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        classService.deleteClass(id);
    }
}
