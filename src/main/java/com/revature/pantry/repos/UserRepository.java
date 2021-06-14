package com.revature.pantry.repos;

import com.revature.pantry.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, QueryByExampleExecutor<User> {

    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);

}
