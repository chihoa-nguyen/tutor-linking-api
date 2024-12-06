package com.nchowf.tutorlinking.token;

import com.nchowf.tutorlinking.enums.Role;
import com.nchowf.tutorlinking.enums.TokenType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Token {
    @Id
    private String token;
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private Role role;
    @Column(nullable = false)
    private TokenType type;
    @ColumnDefault("false")
    public boolean revoked;
    @ColumnDefault("false")
    public boolean expired;
    public Token(Integer userId, Role role, TokenType type) {
        this.userId = userId;
        this.token = UUID.randomUUID().toString();
        this.role = role;
        this.type = type;
    }
}
