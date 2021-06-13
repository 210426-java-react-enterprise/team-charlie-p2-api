package com.revature.pantry.repos;

import com.revature.pantry.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>, QueryByExampleExecutor<Recipe> {

	Optional<Recipe> findById(Integer id);

	Optional<Recipe> findByLabelAndUrlAndImage(String label, String url, String image);

}
