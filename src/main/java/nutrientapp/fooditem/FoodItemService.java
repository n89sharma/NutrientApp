package nutrientapp.fooditem;

import com.mongodb.Mongo;
import nutrientapp.config.SpringMongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FoodItemService {

    private FoodItemRepository foodItemRepository;
    private FoodIdAndDescriptionRepository foodIdAndDescriptionRepository;
    private MeasuresRepository measuresRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public FoodItemService(FoodItemRepository foodItemRepository,
                           FoodIdAndDescriptionRepository foodIdAndDescriptionRepository,
                           MeasuresRepository measuresRepository,
                           Mongo mongo,
                           SpringMongoConfig springMongoConfig) {

        this.foodItemRepository = foodItemRepository;
        this.foodIdAndDescriptionRepository = foodIdAndDescriptionRepository;
        this.measuresRepository = measuresRepository;
        this.mongoTemplate = new MongoTemplate(mongo, springMongoConfig.getDatabaseName());
    }

    public FoodItem getFoodItem(int foodId) {
        return foodItemRepository.findByFoodId(foodId);
    }

    public List<FoodIdAndDescription> getFoodIdAndDescriptions() {
        return foodIdAndDescriptionRepository.findAll();
    }

    public Measures getConversionFactors(int foodId) {
        return measuresRepository.findByFoodId(foodId);
    }

    public void saveFoodItemForUser(String userId, FoodItem foodItem) {
        foodItem.setUserId(userId);
        mongoTemplate.save(foodItem, "customFoodItems");
    }

    public FoodItem saveFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    public FoodItem updateFoodItem(FoodItemPair foodItemPair) {
        FoodItem existingFoodItem = foodItemRepository.findByFoodId(foodItemPair.getOldFoodItem().getFoodId());
        FoodItem newFoodItem = foodItemPair.getNewFoodItem();
        existingFoodItem.setCalories(newFoodItem.getCalories());
        existingFoodItem.setProtein(newFoodItem.getProtein());
        existingFoodItem.setCarbohydrates(newFoodItem.getCarbohydrates());
        existingFoodItem.setSugars(newFoodItem.getSugars());
        existingFoodItem.setFibre(newFoodItem.getFibre());
        existingFoodItem.setFats(newFoodItem.getFats());
        existingFoodItem.setSaturatedFats(newFoodItem.getSaturatedFats());
        existingFoodItem.setTransFats(newFoodItem.getTransFats());
        existingFoodItem.setCholesterol(newFoodItem.getCholesterol());
        existingFoodItem.setSodium(newFoodItem.getSodium());
        existingFoodItem.setIron(newFoodItem.getIron());
        existingFoodItem.setCalcium(newFoodItem.getCalcium());
        existingFoodItem.setVitaminA(newFoodItem.getVitaminA());
        existingFoodItem.setVitaminB12(newFoodItem.getVitaminB12());
        existingFoodItem.setVitaminC(newFoodItem.getVitaminC());
        existingFoodItem.setVitaminD(newFoodItem.getVitaminD());
        existingFoodItem.setOmega3(newFoodItem.getOmega3());
        existingFoodItem.setOmega6(newFoodItem.getOmega6());
        foodItemRepository.save(existingFoodItem);
        return existingFoodItem;
    }

    public enum NutrientEffeciencyFactors {
        CALORY_PERCENT_FROM_FAT, CALORY_PERCENT_FROM_PROTEIN, CALORY_PERCENT_FROM_CARBOHYDRATE, PERCENT_FAT, PERCENT_PROTEIN, PERCENT_CARBOHYRATE;
    }

    public List<FoodItemEffeciency> getFoodItemsForNutrientEffeciency(
        int numberOfItems, 
        NutrientEffeciencyFactors nutrientEffeciencyFactor, 
        boolean showBestItems,
        boolean show0Percent,
        boolean show100Percent) {

        List<FoodIdAndDescription> foodIdAndDescriptions = foodIdAndDescriptionRepository.findAll();
        List<FoodItemEffeciency> foodItemEffeciencies = new ArrayList<>();
        if(nutrientEffeciencyFactor.equals(NutrientEffeciencyFactors.CALORY_PERCENT_FROM_FAT)) {
            foodItemEffeciencies = getFoodItemEffeciencies(foodIdAndDescriptions, FoodItem::getCaloryPercentFromFat);
        }
        else if(nutrientEffeciencyFactor.equals(NutrientEffeciencyFactors.CALORY_PERCENT_FROM_PROTEIN)) {
            foodItemEffeciencies = getFoodItemEffeciencies(foodIdAndDescriptions, FoodItem::getCaloryPercentFromProtein);            
        }
        else if(nutrientEffeciencyFactor.equals(NutrientEffeciencyFactors.CALORY_PERCENT_FROM_CARBOHYDRATE)) {
            foodItemEffeciencies = getFoodItemEffeciencies(foodIdAndDescriptions, FoodItem::getCaloryPercentFromCarbohydrates);
        }

        Comparator<FoodItemEffeciency> compareEffeciency;
        if(showBestItems) {
             compareEffeciency = Comparator.comparing(FoodItemEffeciency::getEffeciency).reversed();
        }
        else{
            compareEffeciency = Comparator.comparing(FoodItemEffeciency::getEffeciency);
        }
            
        return foodItemEffeciencies
            .stream()
            .sorted(compareEffeciency)
            .filter(foodEff -> foodEff.getEffeciency() > 0.05 || show0Percent)
            .filter(foodEff -> foodEff.getEffeciency() < 0.99 || show100Percent)
            .limit(numberOfItems)
            .collect(Collectors.toList());
    }

    private List<FoodItemEffeciency> getFoodItemEffeciencies(List<FoodIdAndDescription> foodIdAndDescriptions, Function<FoodItem, Double> getEffeciencyFromFoodItem) {
        List<FoodItemEffeciency> foodItemEffeciencies = new ArrayList<>();
        for(FoodIdAndDescription foodIdAndDesc : foodIdAndDescriptions) {
            FoodItem foodItem = foodItemRepository.findByFoodId(foodIdAndDesc.getFoodId());
            FoodItemEffeciency foodItemEffeciency = FoodItemEffeciency.of(foodItem.getFoodId(), foodItem.getFoodDescription(), getEffeciencyFromFoodItem.apply(foodItem));
            foodItemEffeciencies.add(foodItemEffeciency);
        }
        return foodItemEffeciencies;
    }
}