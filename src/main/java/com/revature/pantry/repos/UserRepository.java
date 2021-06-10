package com.revature.pantry.repos;

import com.revature.pantry.models.Recipe;
import com.revature.pantry.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, QueryByExampleExecutor<User> {

    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);
    
    User findUserById(int id);


}
