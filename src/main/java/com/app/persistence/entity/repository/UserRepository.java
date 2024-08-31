package com.app.persistence.entity.repository;

import com.app.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username); //Asi seria con QueryMethod
//    @Query("SELECT u FROM UserEntity u WHERE u.username = ?")
  //  Optional<UserEntity> findUser(String username);
}
