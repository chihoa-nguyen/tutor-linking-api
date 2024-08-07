package com.nchowf.tutorlinking.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentRepo extends JpaRepository<Parent,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<Parent> findByEmail(String email);
    List<Parent> findAllByIsEnableTrue();
}
