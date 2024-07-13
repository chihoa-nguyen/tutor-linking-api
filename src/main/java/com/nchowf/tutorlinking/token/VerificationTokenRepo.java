package com.nchowf.tutorlinking.token;

import com.nchowf.tutorlinking.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Integer> {
    VerificationToken findByTokenAndRole(String token, Role role);
}
