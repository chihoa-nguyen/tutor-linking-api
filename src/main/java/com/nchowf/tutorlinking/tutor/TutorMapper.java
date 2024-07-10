package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.tutor.dto.TutorRequest;
import com.nchowf.tutorlinking.utils.enums.Gender;
import com.nchowf.tutorlinking.utils.enums.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TutorMapper {
    @Named("intToGender")
    static Gender intToGender(int gender ){
        return gender == 0 ? Gender.MALE : Gender.FEMALE;
    }
    @Named("intToPosition")
    static Position intToPosition(int position) {
        return position == 0 ? Position.STUDENT :
                (position == 1 ? Position.TEACHER : Position.GRADUATED_STUDENT);
    }
    @Mapping(target = "subjects", ignore = true)
    @Mapping(target = "grades", ignore = true)
    @Mapping(target = "avt", ignore = true)
    @Mapping(target = "degree", ignore = true)
    @Mapping(target = "gender", source = "gender", qualifiedByName = "intToGender")
    @Mapping(target = "position", source = "position", qualifiedByName = "intToPosition")
    Tutor toTutor(TutorRequest request);
    //TutorResponse tuTutorResponse(Tutor tutor);
}
