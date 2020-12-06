package filip.zg.rznuback.web.users;

import filip.zg.rznuback.domain.Recipe;
import filip.zg.rznuback.domain.User;
import filip.zg.rznuback.service.RecipeService;
import filip.zg.rznuback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RecipeService recipeService;

    @PutMapping
    public void saveUser(@RequestBody User user) throws Exception {
        userService.saveUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/{id}")
    public void getUsers(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/{id}/recipes/{recipeId}")
    public Recipe getRecipe(@PathVariable("recipeId") Long recipeId) throws Exception {
       return recipeService.getRecipe(recipeId);
    }

    @PostMapping("/{userId}/recipes")
    public User saveRecipe(@PathVariable("userId") Long userId, @RequestBody Recipe recipe) throws Exception {
        return userService.saveUserRecipe(userId, recipe);
    }

    @DeleteMapping("/{id}/recipes/{recipeId}")
    public void deleteRecipe(@PathVariable("recipeId") Long recipeId, @PathVariable("id") Long id) {
        userService.deleteRecipe(id, recipeId);
    }
}
