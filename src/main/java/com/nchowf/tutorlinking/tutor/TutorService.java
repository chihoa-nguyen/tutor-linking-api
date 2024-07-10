package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.tutor.dto.TutorResponse;
import com.nchowf.tutorlinking.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TutorService implements UserService<TutorRequest, TutorResponse> {
    private final TutorRepo tutorRepo;
    @Override
    public TutorResponse register(TutorRequest request) {
        return null;
    }

    @Override
    public TutorResponse update(Integer id, TutorRequest request) {
        return null;
    }

    @Override
    public TutorResponse getById(Integer id) {
        return null;
    }

    @Override
    public List<TutorResponse> getAll() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
