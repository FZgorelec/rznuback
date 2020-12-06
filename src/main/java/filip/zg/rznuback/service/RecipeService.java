package filip.zg.rznuback.service;

import filip.zg.rznuback.domain.Recipe;
import filip.zg.rznuback.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe getRecipe(Long recipeId) throws Exception {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if (recipe.isPresent())return recipe.get();
       else throw new Exception("No recipe with this id found");
    }

    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }
}
