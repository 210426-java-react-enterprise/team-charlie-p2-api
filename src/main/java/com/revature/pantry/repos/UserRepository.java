package com.revature.pantry.repos;

import com.revature.pantry.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);
    
    User findUserById(int id);

}
