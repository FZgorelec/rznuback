package filip.zg.rznuback.web.users;

import filip.zg.rznuback.domain.Recipe;
import filip.zg.rznuback.domain.User;
import filip.zg.rznuback.service.RecipeService;
import filip.zg.rznuback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RecipeService recipeService;

    @PutMapping
    public void saveUser(User user){
        userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public void getUser(@PathVariable("id") Long userId){
        userService.getUser(userId);
    }

    @GetMapping("/{id}/recipe/{recipeId}")
    public void getRecipe(@PathVariable("recipeId") Long recipeId){
        recipeService.getRecipe(recipeId);
    }

    @PostMapping("/{id}/recipe")
    public User getRecipe(@PathVariable("userId") Long userId, @RequestBody Recipe recipe){
        User user = userService.getUser(userId);
        user.getRecipeModifications().add(recipe);
       return userService.saveUser(user);
    }

    @DeleteMapping("/{id}/recipe/{recipeId}")
    public void deleteRecipe(@PathVariable("recipeId") Long recipeId){
        recipeService.deleteRecipe(recipeId);
    }
}
