package fooditem;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodItemController {
    private FoodItemService foodItemService;

    @Autowired
    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @RequestMapping("/foodItem")
    @ResponseBody
    public FoodItem getFoodItem(int foodId) {
        return foodItemService.getFoodItem(foodId);
    }

    @RequestMapping("/foodItems")
    @ResponseBody
    public List<FoodIdAndDescription> getFoodItems() {
        return foodNames
            .stream()
            .map(foodName -> FoodIdAndDescription.of(foodName.getFoodId(), foodName.getFoodDescription()))
            .collect(toList());
    }

    @RequestMapping("/conversionOptions")
    @ResponseBody
    public Map<String, Double> getConversionOptions(int foodId) {
        List<ConversionFactor> conversionFactors = conversionFactorsPerFoodId.get(foodId);

        return conversionFactors
            .stream()
            .collect(toMap(
                f-> measureNamesPerMeasureId.get(f.getMeasureId()).getMeasureDescription(),
                ConversionFactor::getConversionFactorValue));
    }
}