package com.revature.pantry.repos;

import com.revature.pantry.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	Optional<Recipe> findById(Integer id);

	Optional<Recipe> findByUrl(String url);

}
