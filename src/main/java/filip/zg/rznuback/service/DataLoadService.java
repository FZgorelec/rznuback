package filip.zg.rznuback.service;

import filip.zg.rznuback.domain.Recipe;
import filip.zg.rznuback.domain.User;
import filip.zg.rznuback.domain.enums.Role;
import filip.zg.rznuback.repository.RecipeRepository;
import filip.zg.rznuback.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataLoadService implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Recipe recipe = Recipe.builder().description("Recipe description").ingredients("Meat, cheese, pepper, bread...").name("Meat sandwich").modification(false).build();
        Recipe recipe2 = Recipe.builder().description("Recipe description").ingredients("Fish, cheese, pepper, bread...").name("Fish sandwich").modification(false).build();
        Recipe recipeModification = Recipe.builder().description("Recipe modified description").ingredients("Meat, cheese, pepper, onions, bread...").modification(true).name("Meat onion sandwich").build();

        User user = User.builder().name("Admin").chatType("Polling").password("$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS").role(Role.ADMIN).username("admin").build();
        User user2 = User.builder().name("User").chatType("Polling").password("$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS").role(Role.USER).username("user").build();
        userRepository.save(user);
        userRepository.save(user2);
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipeModification);
        user2.setRecipeModifications(recipes);
        recipeRepository.save(recipe);
        recipeRepository.save(recipe2);
        recipeRepository.save(recipeModification);
        userRepository.save(user2);
    }
}
