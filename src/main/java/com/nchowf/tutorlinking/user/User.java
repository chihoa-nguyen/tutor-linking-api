package com.nchowf.tutorlinking.user;

import com.nchowf.tutorlinking.enums.Role;
import com.nchowf.tutorlinking.utils.AbstractEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class User extends AbstractEntity {
    private String name;
    private String email;
    private String password;
    private Role role;
    private boolean isEnable = false;
}
