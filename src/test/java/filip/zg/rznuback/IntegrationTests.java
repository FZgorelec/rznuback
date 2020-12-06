package filip.zg.rznuback;

import com.fasterxml.jackson.databind.ObjectMapper;
import filip.zg.rznuback.domain.Recipe;
import filip.zg.rznuback.domain.User;
import filip.zg.rznuback.domain.enums.Role;
import filip.zg.rznuback.repository.RecipeRepository;
import filip.zg.rznuback.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
public class IntegrationTests {


    @Autowired
    MockMvc mockMvc;
    private String API_LINK_USERS = "/users";
    private String API_LINK_RECIPES = "/recipes";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        recipeRepository.deleteAll();
    }

    @Test
    void testUsersGet() throws Exception {

        userRepository.save(getUser("admin"));
        userRepository.save(getUser("user2"));

        var response = mockMvc.perform(
                get(API_LINK_USERS).header("Authorization", "Bearer " + createToken("admin"))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(2, objectMapper.readValue(response.getContentAsString(), User[].class).length);
    }

    @Test
    void testUserGet() throws Exception {

        userRepository.save(getUser("admin"));
        User user = userRepository.save(getUser("user2"));

        var response = mockMvc.perform(
                get(API_LINK_USERS + '/' + user.getId()).header("Authorization", "Bearer " + createToken("admin"))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals("user2", objectMapper.readValue(response.getContentAsString(), User.class).getUsername());
    }

    @Test
    void testUserCreate() throws Exception {

        userRepository.save(getUser("admin"));
        User user = getUser("user2");
        user.setId(null);
        mockMvc.perform(
                put(API_LINK_USERS).header("Authorization", "Bearer " + createToken("admin"))
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(user.getName(), userRepository.findByUsername("user2").get().getName());
    }

    @Test
    void testUserUpdate() throws Exception {

        userRepository.save(getUser("admin"));
        User user = userRepository.save(getUser("user2"));
        user.setName("updatedName");
        mockMvc.perform(
                put(API_LINK_USERS).header("Authorization", "Bearer " + createToken("admin"))
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(user.getName(), userRepository.findByUsername("user2").get().getName());
    }

    @Test
    void testUserDelete() throws Exception {

        userRepository.save(getUser("admin"));
        User user = userRepository.save(getUser("user2"));
        mockMvc.perform(
                delete(API_LINK_USERS + "/" + user.getId()).header("Authorization", "Bearer " + createToken("admin"))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    void testRecipesGet() throws Exception {

        userRepository.save(getUser("admin"));
        recipeRepository.save(getRecipe("Recipe"));
        recipeRepository.save(getRecipe("Recipe2"));

        var response = mockMvc.perform(
                get(API_LINK_RECIPES).header("Authorization", "Bearer " + createToken("admin"))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(2, objectMapper.readValue(response.getContentAsString(), Recipe[].class).length);
    }

    @Test
    void testRecipeGet() throws Exception {

        userRepository.save(getUser("admin"));
        Recipe recipe = recipeRepository.save(getRecipe("recipe name"));

        var response = mockMvc.perform(
                get(API_LINK_RECIPES + '/' + recipe.getId()).header("Authorization", "Bearer " + createToken("admin"))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals("recipe name", objectMapper.readValue(response.getContentAsString(), Recipe.class).getName());
    }

    @Test
    void testRecipeCreate() throws Exception {

        userRepository.save(getUser("admin"));
        Recipe recipe = getRecipe("recipe name");
        recipe.setId(null);
        mockMvc.perform(
                put(API_LINK_RECIPES).header("Authorization", "Bearer " + createToken("admin"))
                        .content(objectMapper.writeValueAsString(recipe))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(recipe.getName(), recipeRepository.findByName("recipe name").get().getName());
    }

    @Test
    void testRecipeUpdate() throws Exception {

        userRepository.save(getUser("admin"));
        Recipe recipe = recipeRepository.save(getRecipe("recipe name"));
        recipe.setName("updatedName");
        mockMvc.perform(
                put(API_LINK_RECIPES).header("Authorization", "Bearer " + createToken("admin"))
                        .content(objectMapper.writeValueAsString(recipe))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(recipe.getName(), recipeRepository.findByName("updatedName").get().getName());
    }

    @Test
    void testRecipeDelete() throws Exception {

        userRepository.save(getUser("admin"));
        Recipe recipe = recipeRepository.save(getRecipe("recipe1"));
        recipeRepository.save(getRecipe("recipe2"));
        mockMvc.perform(
                delete(API_LINK_RECIPES + "/" + recipe.getId()).header("Authorization", "Bearer " + createToken("admin"))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(1, recipeRepository.findAll().size());
    }

    @Test
    void testUserRecipeGet() throws Exception {

        User user = userRepository.save(getUser("admin"));
        Recipe recipe = recipeRepository.save(getRecipe("recipe name"));
        user.getRecipeModifications().add(recipe);
        userRepository.save(user);


        var response = mockMvc.perform(
                get(API_LINK_USERS + '/' + user.getId() + API_LINK_RECIPES + "/" + recipe.getId()).header("Authorization", "Bearer " + createToken("admin"))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals("recipe name", objectMapper.readValue(response.getContentAsString(), Recipe.class).getName());
    }

    @Test
    void testUserRecipeCreate() throws Exception {

        User user = userRepository.save(getUser("admin"));
        Recipe recipe = getRecipe("recipe name");
        recipe.setId(null);
        mockMvc.perform(
                post(API_LINK_USERS + '/' + user.getId() + API_LINK_RECIPES).header("Authorization", "Bearer " + createToken("admin"))
                        .content(objectMapper.writeValueAsString(recipe))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(recipe.getName(), userRepository.findById(user.getId()).get().getRecipeModifications().get(0).getName());
    }

    @Test
    void testUserRecipeUpdate() throws Exception {

        User user = userRepository.save(getUser("admin"));
        Recipe recipe = recipeRepository.save(getRecipe("recipe name"));
        user.getRecipeModifications().add(recipe);
        userRepository.save(user);
        recipe.setName("updatedName");
        mockMvc.perform(
                put(API_LINK_RECIPES).header("Authorization", "Bearer " + createToken("admin"))
                        .content(objectMapper.writeValueAsString(recipe))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(recipe.getName(),  userRepository.findById(user.getId()).get().getRecipeModifications().get(0).getName());
    }

    @Test
    void testUserRecipeDelete() throws Exception {

        User user = userRepository.save(getUser("admin"));
        Recipe recipe = recipeRepository.save(getRecipe("recipe name"));
        user.getRecipeModifications().add(recipe);
        userRepository.save(user);


        mockMvc.perform(
                delete(API_LINK_USERS + '/' + user.getId() + API_LINK_RECIPES + "/" + recipe.getId()).header("Authorization", "Bearer " + createToken("admin"))
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertEquals(0, recipeRepository.findAll().size());
    }

    static String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 1000 * 60 * 60 * 2))
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
    }


    private User getUser(String username) {
        return User.builder().name("Admin").password("$2a$10$mR4MU5esBbUd6JWuwWKTA.tRy.jo4d4XRkgnamcOJfw5pJ8Ao/RDS").role(Role.ADMIN).username(username).recipeModifications(new ArrayList<>()).build();
    }

    private Recipe getRecipe(String recipe) {
        return Recipe.builder().description("Recipe description").ingredients("Meat, cheese, pepper, bread...").name(recipe).modification(false).build();
    }
}
