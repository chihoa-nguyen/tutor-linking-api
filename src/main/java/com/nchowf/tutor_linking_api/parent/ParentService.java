package com.nchowf.tutor_linking_api.parent;

import com.nchowf.tutor_linking_api.exception.AppException;
import com.nchowf.tutor_linking_api.exception.ErrorCode;
import com.nchowf.tutor_linking_api.parent.dto.ParentRequest;
import com.nchowf.tutor_linking_api.parent.dto.ParentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentService {
    private final ParentRepo parentRepo;
    private final ParentMapper parentMapper;


    public ParentResponse create(ParentRequest request){
        if (parentRepo.existsByPhoneNumber(request.getPhoneNumber()))
            throw new AppException(ErrorCode.PHONE_NUMBER_USED);
        if (parentRepo.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        return parentMapper.toParentResponse(parentRepo
                .save(parentMapper.toParent(request)));
    }

    public ParentResponse getById(Integer id) {
        Parent parent = parentRepo.findById(id)
                .orElseThrow(() -> new AppException((ErrorCode.USER_NOT_EXISTED)));
        return parentMapper.toParentResponse(parent);
    }
    public List<ParentResponse> getAll() {
        return parentRepo.findAll().stream()
                .map(parentMapper::toParentResponse).toList();
    }
    public ParentResponse update(Integer id, ParentRequest parentRequest) {
        Parent parent = parentRepo.findById(id)
                .orElseThrow(() -> new AppException((ErrorCode.USER_NOT_EXISTED)));
        parentMapper.toUpdateParent(parent, parentRequest);
        return parentMapper.toParentResponse(parentRepo
               .save(parent));
    }
}
