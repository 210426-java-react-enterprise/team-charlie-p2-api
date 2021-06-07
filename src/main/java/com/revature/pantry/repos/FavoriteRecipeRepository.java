package com.revature.pantry.repos;

import com.revature.pantry.models.UserFavoriteRecipe;
import com.revature.pantry.models.UserRecipeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface FavoriteRecipeRepository extends JpaRepository <UserFavoriteRecipe, UserRecipeId> {

    @Modifying
    @Query(value = "from UserFavoriteRecipe where user_id = :id")
    Set<UserFavoriteRecipe> findByUserId(@Param("id") int id);

}
