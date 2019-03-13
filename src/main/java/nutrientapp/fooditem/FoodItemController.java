package nutrientapp.fooditem;

import nutrientapp.domain.csvobjects.FoodCsv;
import nutrientapp.domain.csvobjects.MeasureNameCsv;
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
    public FoodCsv getFoodItem(@PathVariable int foodId) {
        return foodItemService.getFoodItem(foodId);
    }

    @RequestMapping(value = "/food/{foodId}/conversion-factors", method = GET)
    @ResponseBody
    public List<MeasureNameCsv> getConversionFactors(@PathVariable int foodId) {
        return foodItemService.getConversionFactors(foodId);
    }
}