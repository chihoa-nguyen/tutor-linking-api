package com.nchowf.tutorlinking.classes;

import com.nchowf.tutorlinking.parent.Parent;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClassRepo extends JpaRepository<Class, Integer>, JpaSpecificationExecutor<Class> {
    Optional<Class> findById(Integer id);
    @Query(value = " CALL GET_SUITABLE_CLASSES(?) ", nativeQuery = true)
    List<Class> getClassesSuitableForTutor(Integer tutorId);
    List<Class> findAllByParent(Parent parent);
}
