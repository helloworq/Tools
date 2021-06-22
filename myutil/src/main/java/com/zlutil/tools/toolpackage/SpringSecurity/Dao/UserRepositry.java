package com.zlutil.tools.toolpackage.SpringSecurity.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = UserEntity.class, idClass = String.class)
public interface UserRepositry extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
}
