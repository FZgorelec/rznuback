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
    public Recipe putRecipe(@RequestBody Recipe recipe){
        return recipeService.saveRecipe(recipe);
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable Long id) throws Exception {
        return recipeService.getRecipe(id);
    }

    @DeleteMapping("/{recipeId}")
    public void deleteRecipe(@PathVariable("recipeId") Long recipeId){
        recipeService.deleteRecipe(recipeId);
    }


}
