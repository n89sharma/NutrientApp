import nutrientapp.NutrientApp;
import nutrientapp.domain.repositories.ConversionFactorRepository;
import nutrientapp.domain.repositories.FoodGroupRepository;
import nutrientapp.domain.repositories.FoodRepository;
import nutrientapp.domain.repositories.MeasureNameRepository;
import nutrientapp.domain.repositories.NutrientAmountRepository;
import nutrientapp.domain.repositories.NutrientNameRepository;
import nutrientapp.domain.repositories.RefuseAmountRepository;
import nutrientapp.domain.repositories.RefuseNameRepository;
import nutrientapp.domain.repositories.YieldAmountRepository;
import nutrientapp.domain.repositories.YieldNameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static nutrientapp.utils.CsvFiles.CONVERSION_FACTOR;
import static nutrientapp.utils.CsvFiles.FOOD_GROUP;
import static nutrientapp.utils.CsvFiles.FOOD_NAME;
import static nutrientapp.utils.CsvFiles.MEASURE_NAME;
import static nutrientapp.utils.CsvFiles.NUTRIENT_AMOUNT;
import static nutrientapp.utils.CsvFiles.NUTRIENT_NAME;
import static nutrientapp.utils.CsvFiles.REFUSE_AMOUNT;
import static nutrientapp.utils.CsvFiles.REFUSE_NAME;
import static nutrientapp.utils.CsvFiles.YIELD_AMOUNT;
import static nutrientapp.utils.CsvFiles.YIELD_NAME;
import static nutrientapp.utils.CsvFiles.getAllTableRowsFrom;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NutrientApp.class})
public class GeneralTest {

    @Autowired
    ConversionFactorRepository conversionFactorRepository;
    @Autowired
    FoodGroupRepository foodGroupRepository;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    MeasureNameRepository measureNameRepository;
    @Autowired
    NutrientAmountRepository nutrientAmountRepository;
    @Autowired
    NutrientNameRepository nutrientNameRepository;
    @Autowired
    RefuseAmountRepository refuseAmountRepository;
    @Autowired
    RefuseNameRepository refuseNameRepository;
    @Autowired
    YieldNameRepository yieldNameRepository;
    @Autowired
    YieldAmountRepository yieldAmountRepository;

    @Test
    public void saveAllCsvFilesInDatabase() {
        conversionFactorRepository.save(getAllTableRowsFrom(CONVERSION_FACTOR));
        foodGroupRepository.save(getAllTableRowsFrom(FOOD_GROUP));
        foodRepository.save(getAllTableRowsFrom(FOOD_NAME));
        measureNameRepository.save(getAllTableRowsFrom(MEASURE_NAME));
        nutrientAmountRepository.save(getAllTableRowsFrom(NUTRIENT_AMOUNT));
        nutrientNameRepository.save(getAllTableRowsFrom(NUTRIENT_NAME));
        refuseAmountRepository.save(getAllTableRowsFrom(REFUSE_AMOUNT));
        refuseNameRepository.save(getAllTableRowsFrom(REFUSE_NAME));
        yieldAmountRepository.save(getAllTableRowsFrom(YIELD_AMOUNT));
        yieldNameRepository.save(getAllTableRowsFrom(YIELD_NAME));
    }
}
