package nutrientapp.recipe;

import nutrientapp.domain.internal.Food;
import nutrientapp.domain.internal.Measure;
import nutrientapp.domain.internal.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(value = "/{userId}/recipe", method = POST)
    @ResponseBody
    public Recipe saveRecipe(@RequestBody Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }

    @RequestMapping(value = "/{userId}/recipe/{recipeId}/measure", method = GET)
    @ResponseBody
    public List<Measure> getRecipeMeasure(@PathVariable String userId, @PathVariable String recipeId) {
        return recipeService.getRecipeMeasure(recipeId);
    }

    @RequestMapping(value = "/{userId}/recipe/{recipeId}", method = GET)
    @ResponseBody
    public Food getRecipe(
        @PathVariable String userId,
        @PathVariable String recipeId,
        @RequestParam(required = false, defaultValue = "1.0") Double serving) {

        return recipeService.getRecipeAsFoodItem(recipeId, serving);
    }

}
