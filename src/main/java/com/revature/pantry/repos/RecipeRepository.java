package com.revature.pantry.repos;

import com.revature.pantry.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;
import java.util.Set;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	Optional<Recipe> findById(Integer id);

	Optional<Recipe> findByUrl(String url);


//	@Modifying
//	@Query(value = "from Recipe inner join ");
//	Set<Recipe> findByUserId(int userId);

}
