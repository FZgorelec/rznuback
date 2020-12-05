package filip.zg.rznuback.web.recipes;

import filip.zg.rznuback.domain.Recipe;
import filip.zg.rznuback.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public List<Recipe> getRecipes(){
        return recipeService.getRecipes();
    }

    @PutMapping
    public Recipe putRecipe(Recipe recipe){
        return recipeService.saveRecipe(recipe);
    }

    @DeleteMapping("/{recipeId}")
    public void putRecipe(@PathVariable("recipeId") Long recipeId){
        recipeService.deleteRecipe(recipeId);
    }


}
