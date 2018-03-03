package nutrientapp.fooditem;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoodItemController {

    private FoodItemService foodItemService;
    private DataInput dataInput;

    @Autowired
    public FoodItemController(FoodItemService foodItemService, DataInput dataInput) {
        this.foodItemService = foodItemService;
        this.dataInput = dataInput;
        //dataInput.saveItemsToRepository();
    }

    @RequestMapping(value = "/foodItem", method = RequestMethod.GET)
    @ResponseBody
    public FoodItem getFoodItem(int foodId) {
        return foodItemService.getFoodItem(foodId);
    }

    @RequestMapping(value = "/foodItems", method = RequestMethod.GET)
    @ResponseBody
    public List<FoodIdAndDescription> getFoodIdAndDescriptions() {
        return foodItemService.getFoodIdAndDescriptions();
    }

    @RequestMapping(value = "/conversionOptions", method = RequestMethod.GET)
    @ResponseBody
    public Measures getConversionOptions(int foodId) {
        return foodItemService.getConversionFactors(foodId);
    }
}