package nutrientapp.fooditem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {

    private FoodItemRepository foodItemRepository;
    private FoodIdAndDescriptionRepository foodIdAndDescriptionRepository;
    private ConversionFactorRepository conversionFactorRepository;

    @Autowired
    public FoodItemService(FoodItemRepository foodItemRepository,
                           FoodIdAndDescriptionRepository foodIdAndDescriptionRepository,
                           ConversionFactorRepository conversionFactorRepository) {

        this.foodItemRepository = foodItemRepository;
        this.foodIdAndDescriptionRepository = foodIdAndDescriptionRepository;
        this.conversionFactorRepository = conversionFactorRepository;
    }

    public FoodItem getFoodItem(int foodId) {
        return foodItemRepository.findByFoodId(foodId);
    }

    public List<FoodIdAndDescription> getFoodIdAndDescriptions() {
        return foodIdAndDescriptionRepository.findAll();
    }

    public Measures getConversionFactors(int foodId) {
        return conversionFactorRepository.findByFoodId(foodId);
    }
}