package com.nchowf.tutorlinking.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepo extends JpaRepository<Parent,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
}
