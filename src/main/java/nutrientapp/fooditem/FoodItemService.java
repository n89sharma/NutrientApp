package nutrientapp.fooditem;

import lombok.val;
import nutrientapp.domain.repositories.FoodRepository;
import nutrientapp.domain.repositories.NutrientAmountRepository;
import nutrientapp.domain.repositories.NutrientNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodItemService {

    private FoodRepository foodRepository;
    private NutrientAmountRepository nutrientAmountRepository;
    private NutrientNameRepository nutrientNameRepository;

    @Autowired
    public FoodItemService(
            FoodRepository foodRepository,
            NutrientNameRepository nutrientNameRepository,
            NutrientAmountRepository nutrientAmountRepository) {

        this.foodRepository = foodRepository;
        this.nutrientAmountRepository = nutrientAmountRepository;
        this.nutrientNameRepository = nutrientNameRepository;
    }

    public Food getFoodItem(int foodId) {
        val foodCsv = foodRepository.findByFoodId(foodId);
        val nutrientAmounts = nutrientAmountRepository.findByFoodId(foodId);
        return null;
    }
}