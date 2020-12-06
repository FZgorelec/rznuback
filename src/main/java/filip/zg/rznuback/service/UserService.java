package filip.zg.rznuback.service;

import filip.zg.rznuback.domain.Recipe;
import filip.zg.rznuback.domain.User;
import filip.zg.rznuback.repository.RecipeRepository;
import filip.zg.rznuback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    private final PasswordEncoder encoder;

    public User saveUser(User user) throws Exception {
        if (user.getId() == null) {
            user.setPassword(encoder.encode(user.getPassword()));
        }else {
            User savedUser = userRepository.getOne(user.getId());
            if (savedUser == null) throw new Exception("No user with this id found");
            user.setPassword(savedUser.getPassword());
        }
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User saveUserRecipe(Long userId, Recipe recipe) throws Exception {
        User user = getUser(userId);
        Long recipeId = recipe.getId();
        if (recipeId == null) {
            recipe = recipeRepository.save(recipe);
            user.getRecipeModifications().add(recipe);
        }
        else {
            Optional<Recipe> savedRecipe = user.getRecipeModifications().stream().filter((recipe1 -> recipe1.getId().equals(recipeId))).findFirst();
            if (savedRecipe.isPresent()) {
                savedRecipe.get().setName(recipe.getName());
                savedRecipe.get().setDescription(recipe.getDescription());
                savedRecipe.get().setIngredients(recipe.getDescription());
            } else throw new Exception("No Recipe with this id found");
        }
        return saveUser(user);
    }

    @Transactional
    public void deleteRecipe(Long id, Long recipeId) {
        User user = userRepository.getOne(id);
        user.getRecipeModifications().removeIf((recipe -> recipe.getId().equals(recipeId)));
        userRepository.save(user);
        recipeRepository.deleteById(recipeId);

    }
}
