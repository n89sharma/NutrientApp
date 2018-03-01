package fooditem;

import dbobjects.FoodName;
import dbobjects.NutrientAmount;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.groupingBy;

public class FoodItemService {

    private Map<Integer, FoodName> foodNamesPerFoodId;    
    private Map<Integer, List<NutrientAmount>> nutrientAmountsPerFoodId;
    private FoodItemRepository foodItemRepository;
    
    //Saving data to repository
    public void saveItemsToRepository(FoodItem foodItem) {
        List<FoodItem> foodItems = foodNamesPerFoodId
            .values()
            .stream()
            .map(foodName -> FoodItem.of(foodName, nutrientAmountsPerFoodId.get(foodName.getFoodId())))
            .collect(Collectors.toList());
        foodItemRepository.save(foodItems);
    }
    //---------------------------------------------------------------------------------------------------------

    public FoodItemService(Map<Integer, FoodName> foodNamesPerFoodId, List<NutrientAmount> nutrientAmounts) {
        this.foodNamesPerFoodId = foodNamesPerFoodId;
        this.nutrientAmountsPerFoodId = nutrientAmounts
        .stream()
        .collect(groupingBy(NutrientAmount::getFoodId, toList()));
    }

    public FoodItem getFoodItem(int foodId) {
        FoodItem foodItem = null;
        if(foodItemRepository.exists(foodId)) {
            foodItem = foodItemRepository.findOne(foodId);
        }
        return foodItem;
    }
}