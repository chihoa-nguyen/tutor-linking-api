package com.nchowf.tutorlinking.classes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ClassRepo extends JpaRepository<Class,Integer>, JpaSpecificationExecutor<Class> {
    Optional<Class> findById(Integer id);
    List<Class> findAllByHasTutorFalse();
}
