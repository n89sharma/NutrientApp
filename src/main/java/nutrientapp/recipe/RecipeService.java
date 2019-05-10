package nutrientapp.recipe;

import lombok.val;
import nutrientapp.domain.internal.Food;
import nutrientapp.domain.internal.ItemSummary;
import nutrientapp.domain.internal.Measure;
import nutrientapp.domain.internal.Recipe;
import nutrientapp.domain.repositories.RecipeRepository;
import nutrientapp.fooditem.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;
    private FoodItemService foodItemService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, FoodItemService foodItemService) {
        this.recipeRepository = recipeRepository;
        this.foodItemService = foodItemService;
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Measure> getRecipeMeasure(String recipeId) {
        val recipe = recipeRepository.findOne(recipeId);
        val measure = new Measure();
        measure.setMeasureName(recipe.getMeasure());
        measure.setMeasureNameF(recipe.getMeasure());
        return Collections.singletonList(measure);
    }

    public Food getRecipeAsFoodItem(String recipeId, double serving) {
        val recipe = recipeRepository.findOne(recipeId);
        val portionIds = recipe.getPortionIds();
        val firstPortion = portionIds.remove(0);
        val firstFood = foodItemService.getFoodItem(
            firstPortion.getFoodId(),
            firstPortion.getMeasureId(),
            firstPortion.getServing());
        val recipeAsFood = portionIds
            .stream()
            .map(portionId -> foodItemService.getFoodItem(portionId.getFoodId(), portionId.getMeasureId(), portionId.getServing()))
            .reduce(firstFood, Food::add);
        recipeAsFood.multiplyByFactor(serving);
        recipeAsFood.setFoodDescription(String.format("%s : %s", recipe.getName(), recipe.getDescription()));
        return recipeAsFood;
    }

    public List<ItemSummary> getUserRecipeSummaries(String userId) {
        return recipeRepository.findByUserId(userId).stream()
            .map(RecipeService::mapRecipe)
            .collect(toList());
    }

    private static ItemSummary mapRecipe(Recipe recipe) {
        return new ItemSummary(
            recipe.getId(),
            recipe.getDescription(),
            recipe.getDescription(),
            ItemSummary.ItemType.RECIPE);
    }
}
