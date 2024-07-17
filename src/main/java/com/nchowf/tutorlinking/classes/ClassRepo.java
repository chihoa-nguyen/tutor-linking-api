package com.nchowf.tutorlinking.classes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClassRepo extends JpaRepository<Class,Integer>, JpaSpecificationExecutor<Class> {
}
