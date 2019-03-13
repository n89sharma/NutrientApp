package nutrientapp.fooditem;

import com.mongodb.Mongo;
import nutrientapp.config.SpringMongoConfig;
import nutrientapp.domain.csvobjects.ConversionFactorCsv;
import nutrientapp.domain.csvobjects.FoodCsv;
import nutrientapp.domain.csvobjects.MeasureNameCsv;
import nutrientapp.domain.repositories.ConversionFactorRepository;
import nutrientapp.domain.repositories.FoodGroupRepository;
import nutrientapp.domain.repositories.FoodRepository;
import nutrientapp.domain.repositories.MeasureNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FoodItemService {

    private FoodRepository foodRepository;
    private ConversionFactorRepository conversionFactorRepository;
    private MeasureNameRepository measureNameRepository;
    private FoodGroupRepository foodGroupRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public FoodItemService(FoodRepository foodRepository,
                           MeasureNameRepository measureNameRepository,
                           FoodGroupRepository foodGroupRepository,
                           Mongo mongo,
                           SpringMongoConfig springMongoConfig) {

        this.foodRepository = foodRepository;
        this.measureNameRepository = measureNameRepository;
        this.foodGroupRepository = foodGroupRepository;
        this.mongoTemplate = new MongoTemplate(mongo, springMongoConfig.getDatabaseName());
    }

    public FoodCsv getFoodItem(int foodId) {
        return foodRepository.findByFoodId(foodId);
    }

    public List<MeasureNameCsv> getConversionFactors(int foodId) {
        return conversionFactorRepository
            .findByFoodId(foodId)
            .stream()
            .map(ConversionFactorCsv::getMeasureId)
            .map(measureId -> measureNameRepository.findByMeasureId(measureId))
            .collect(toList());
    }

    public enum MacroNutrientDensities {
        CALORIE_PERCENT_FROM_FAT,
        CALORIE_PERCENT_FROM_PROTEIN,
        CALORIE_PERCENT_FROM_CARBOHYDRATE;
    }
}