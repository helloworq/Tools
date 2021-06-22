package com.zlutil.tools.toolpackage.SpringSecurity.Dao;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;
    private boolean enable;

}
