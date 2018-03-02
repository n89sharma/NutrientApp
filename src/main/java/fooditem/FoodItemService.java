package fooditem;

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
        FoodItem foodItem = null;
        if (foodItemRepository.exists(foodId)) {
            foodItem = foodItemRepository.findOne(foodId);
        }
        return foodItem;
    }

    public List<FoodIdAndDescription> getFoodIdAndDescriptions() {
        return foodIdAndDescriptionRepository.findAll();
    }

    public ConversionFactorFoodItem getConversionFactors(int foodId) {
        ConversionFactorFoodItem conversionFactorFoodItem = null;
        if (conversionFactorRepository.exists(foodId)) {
            conversionFactorFoodItem = conversionFactorRepository.findOne(foodId);
        }
        return conversionFactorFoodItem;
    }
}