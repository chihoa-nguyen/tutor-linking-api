package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.classes.dto.ClassDetailResponse;
import com.nchowf.tutorlinking.classes.dto.ClassRequest;
import com.nchowf.tutorlinking.classes.dto.ClassResponse;
import com.nchowf.tutorlinking.classes.dto.FilterClassRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;
    @PostMapping("/class")
    public ClassDetailResponse create(@RequestBody @Valid ClassRequest request){
        return classService.createClass(request);
    }
    @PostMapping("/class/send-mail/{id}")
    public void sendMail(@PathVariable  Integer id){
        classService.sendClassToSuitableTutors(id);
    }
    @PostMapping("/classes")
    public List<ClassResponse> getClasses(@RequestBody FilterClassRequest request){
        return classService.getClasses(request);
    }
    @GetMapping("class/parent")
    public List<ClassDetailResponse> getClassesOfThisParent(){
        return classService.getClassesOfThisParent();
    }
    @GetMapping("class/{id}")
    public ClassResponse getById(@PathVariable("id") Integer id){
        return classService.getResponseById(id);
    }
    @GetMapping("class/detail/{id}")
    public ClassDetailResponse getDetailById(@PathVariable("id") Integer id){
        return classService.getDetailsById(classService.getById(id));
    }
    @GetMapping("class/tutor")
    public List<ClassResponse> getClassesSuitableForTutor(){
        return classService.getClassesSuitableForTutor();
    }
    @PutMapping("class/{id}")
    public ClassDetailResponse updateById(@PathVariable("id") Integer id, @RequestBody @Valid ClassRequest request){
        return classService.updateClass(id, request);
    }
    @DeleteMapping("class/{id}")
    public void delete(@PathVariable("id") Integer id) {
        classService.deleteClass(id);
    }
}
