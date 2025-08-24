package com.blessed.userservice.repository;

import com.blessed.userservice.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByEmail(String email);
    public UserEntity findByName(String name);
    public UserEntity findByEmailAndPassword(String email, String password);
    public UserEntity findById(long id);
    public Page<UserEntity> findAll(Pageable pageable);

}
