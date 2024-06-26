package com.nchowf.tutorlinking.parent;

import com.nchowf.tutorlinking.parent.dto.ParentRequest;
import com.nchowf.tutorlinking.parent.dto.ParentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parent")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;
    @PostMapping("")
    @ResponseStatus(value =HttpStatus.CREATED)
    public ParentResponse add(@RequestBody @Valid ParentRequest parentRequest){
        return parentService.create(parentRequest);
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.FOUND)
    public List<ParentResponse> getAll(){
        return parentService.getAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public ParentResponse getById(@PathVariable("id") Integer id){
        return parentService.getById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ParentResponse update(@PathVariable("id") Integer id, @RequestBody @Valid ParentRequest parentRequest){
        return parentService.update(id, parentRequest);
    }
}
