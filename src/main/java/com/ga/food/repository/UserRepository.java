package com.ga.food.repository;

import com.ga.food.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User,Long>{
    boolean existsByEmailAddress(String emailAddress);
    User findUserByEmailAddress(String emailAddress);
    User findByUserName(String userName);
}
