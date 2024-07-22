package com.nchowf.tutorlinking.token;

import com.nchowf.tutorlinking.enums.Role;
import com.nchowf.tutorlinking.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TokenRepo extends JpaRepository<Token, Integer> {
    Token findByTokenAndRoleAndAndType(String token, Role role, TokenType type);
    @Query(value = """
            SELECT t
            FROM Token t
            WHERE t.userId = :id
              AND (t.expired = false OR t.revoked = false)
              AND t.type <> com.nchowf.tutorlinking.enums.TokenType.VERIFICATION""")
    List<Token> findAllValidTokenByUser(Integer id);
}
