package nutrientapp.fooditem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class FoodItemController {

    private FoodItemService foodItemService;

    @Autowired
    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @RequestMapping(value = "/food/{foodId}", method = GET)
    @ResponseBody
    public Food getFoodItem(@PathVariable int foodId) {
        return foodItemService.getFoodItem(foodId);
    }

    @RequestMapping(value = "/food", method = GET)
    public List<FoodSummary> getFoodSummaries() {
        return foodItemService.getFoodItems();
    }

    @RequestMapping(value = "/food/{foodId}/measure", method = GET)
    @ResponseBody
    public List<Measure> getMeasures(@PathVariable int foodId) {
        return foodItemService.getMeasures(foodId);
    }
}