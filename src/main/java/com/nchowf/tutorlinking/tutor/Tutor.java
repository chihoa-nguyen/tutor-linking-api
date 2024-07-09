package com.nchowf.tutorlinking.tutor;

import com.nchowf.tutorlinking.grade.Grade;
import com.nchowf.tutorlinking.subject.Subject;
import com.nchowf.tutorlinking.user.User;
import com.nchowf.tutorlinking.utils.enums.Gender;
import com.nchowf.tutorlinking.utils.enums.Position;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tutor extends User {
    @Column(length = 10)
    private String birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String avt;
    private String degree;
    private String address;
    private String universityName;
    private String major;
    @Enumerated(EnumType.STRING)
    private Position position;
    private String phoneNumber;
    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private List<String> teachingArea;
    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable(name ="tutor_subject")
    private Set<Subject> subjects;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name ="tutor_grade")
    private Set<Grade> grades;
}
