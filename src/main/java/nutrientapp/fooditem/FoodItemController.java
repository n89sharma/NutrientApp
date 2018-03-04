package nutrientapp.fooditem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import nutrientapp.fooditem.FoodItemService.NutrientEffeciencyFactors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class FoodItemController {

    private FoodItemService foodItemService;
    private UserService userService;
    private DataInput dataInput;

    @Autowired
    public FoodItemController(FoodItemService foodItemService, DataInput dataInput, UserService userService) {
        this.foodItemService = foodItemService;
        this.dataInput = dataInput;
        this.userService = userService;
        //dataInput.saveItemsToRepository();
    }

    @RequestMapping(value = "/foodItems/{foodId}", method = GET)
    @ResponseBody
    public FoodItem getFoodItem(@PathVariable int foodId) {
        return foodItemService.getFoodItem(foodId);
    }

    @RequestMapping(value = "/foodItems", method = GET)
    @ResponseBody
    public List<FoodIdAndDescription> getFoodIdAndDescriptions() {
        return foodItemService.getFoodIdAndDescriptions();
    }

    @RequestMapping(value = "/{userId}/foodItems", method = POST)
    @ResponseBody
    public void createCustomFoodItem(@PathVariable String userId, @RequestBody FoodItem foodItem) {
        foodItemService.saveFoodItemForUser(userId, foodItem);
    }

    @RequestMapping(value = "/foodItems/{foodId}/conversionOptions", method = GET)
    @ResponseBody
    public Measures getConversionOptions(@PathVariable int foodId) {
        return foodItemService.getConversionFactors(foodId);
    }

    @RequestMapping(value = "/foodItems", method = POST)
    @ResponseBody
    public FoodItem createFoodItem(@RequestBody FoodItem foodItem) {
        return foodItemService.saveFoodItem(foodItem);
    }

    @RequestMapping(value = "/foodItems/{foodId}", method = PUT)
    @ResponseBody
    public FoodItem updateFoodItem(@RequestBody FoodItemPair foodItemPair) {
        return foodItemService.updateFoodItem(foodItemPair);
    }

    @RequestMapping(value = "/foodItems/topFatEffecient")
    @ResponseBody
    public List<FoodItemEffeciency> getTopFatEffecienctFoodItems(
        @RequestParam int numberOfItems, 
        @RequestParam NutrientEffeciencyFactors nutrientEffeciencyFactor, 
        @RequestParam boolean showBestItems,
        @RequestParam(required = false) boolean show100Percent,
        @RequestParam(required = false) boolean show0Percent) {
        return foodItemService.getFoodItemsForNutrientEffeciency(numberOfItems, nutrientEffeciencyFactor, showBestItems, show0Percent, show0Percent);
    }

    @RequestMapping(value = "/{userId}/weight", method = POST)
    @ResponseBody
    public List<UserWeightAtTime> addWeightAtTime(@RequestBody UserWeightAtTime weightAtTime) {
        return userService.saveWeightAtTime(weightAtTime);
    }

    @RequestMapping(value = "/{userId}/meal", method = POST)
    @ResponseBody
    public List<UserMealAtTime> addMealAtTime(@RequestBody UserMealAtTime mealAtTime) {
        return userService.saveMealAtTime(mealAtTime);
    }
}