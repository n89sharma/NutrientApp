import lombok.val;
import nutrientapp.NutrientApp;
import nutrientapp.domain.csvobjects.*;
import nutrientapp.domain.csvrepositories.ConversionFactorRepository;
import nutrientapp.domain.csvrepositories.FoodGroupRepository;
import nutrientapp.domain.csvrepositories.FoodRepository;
import nutrientapp.domain.csvrepositories.MeasureNameRepository;
import nutrientapp.domain.csvrepositories.NutrientAmountRepository;
import nutrientapp.domain.csvrepositories.NutrientNameRepository;
import nutrientapp.domain.csvrepositories.RefuseAmountRepository;
import nutrientapp.domain.csvrepositories.RefuseNameRepository;
import nutrientapp.domain.csvrepositories.YieldAmountRepository;
import nutrientapp.domain.csvrepositories.YieldNameRepository;
import nutrientapp.domain.databaseobjects.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.junit4.SpringRunner;
import sun.java2d.loops.FillRect;

import java.util.HashMap;
import java.util.function.Function;

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
    ConversionFactorRepository conversionFactorCsvRepository;
    @Autowired
    FoodGroupRepository foodGroupCsvRepository;
    @Autowired
    FoodRepository foodCsvRepository;
    @Autowired
    MeasureNameRepository measureNameCsvRepository;
    @Autowired
    NutrientAmountRepository nutrientAmountCsvRepository;
    @Autowired
    NutrientNameRepository nutrientNameCsvRepository;
    @Autowired
    RefuseAmountRepository refuseAmountCsvRepository;
    @Autowired
    RefuseNameRepository refuseNameCsvRepository;
    @Autowired
    YieldNameRepository yieldNameCsvRepository;
    @Autowired
    YieldAmountRepository yieldAmountCsvRepository;

    @Autowired
    nutrientapp.domain.repositories.ConversionFactorRepository conversionFactorRepository;
    @Autowired
    nutrientapp.domain.repositories.FoodGroupRepository foodGroupRepository;
    @Autowired
    nutrientapp.domain.repositories.FoodRepository foodRepository;
    @Autowired
    nutrientapp.domain.repositories.MeasureNameRepository measureNameRepository;
    @Autowired
    nutrientapp.domain.repositories.NutrientAmountRepository nutrientAmountRepository;
    @Autowired
    nutrientapp.domain.repositories.NutrientNameRepository nutrientNameRepository;
    @Autowired
    nutrientapp.domain.repositories.RefuseAmountRepository refuseAmountRepository;
    @Autowired
    nutrientapp.domain.repositories.RefuseNameRepository refuseNameRepository;
    @Autowired
    nutrientapp.domain.repositories.YieldNameRepository yieldNameRepository;
    @Autowired
    nutrientapp.domain.repositories.YieldAmountRepository yieldAmountRepository;

    @Test
    public void saveAllCsvFilesInDatabase() {
        conversionFactorCsvRepository.save(getAllTableRowsFrom(CONVERSION_FACTOR));
        foodGroupCsvRepository.save(getAllTableRowsFrom(FOOD_GROUP));
        foodCsvRepository.save(getAllTableRowsFrom(FOOD_NAME));
        measureNameCsvRepository.save(getAllTableRowsFrom(MEASURE_NAME));
        nutrientAmountCsvRepository.save(getAllTableRowsFrom(NUTRIENT_AMOUNT));
        nutrientNameCsvRepository.save(getAllTableRowsFrom(NUTRIENT_NAME));
        refuseAmountCsvRepository.save(getAllTableRowsFrom(REFUSE_AMOUNT));
        refuseNameCsvRepository.save(getAllTableRowsFrom(REFUSE_NAME));
        yieldAmountCsvRepository.save(getAllTableRowsFrom(YIELD_AMOUNT));
        yieldNameCsvRepository.save(getAllTableRowsFrom(YIELD_NAME));
    }

    @Test
    public void createNewYieldObjects() {
        val map = createDatabaseObjects(
                yieldAmountCsvRepository,
                yieldNameRepository,
                YieldAmountCsv::getYieldId,
                YieldAmount::getYieldId,
                GeneralTest::map);

        System.out.println();
    }

    public <SR extends MongoRepository<S, String>, S,
            TR extends MongoRepository<T, String>, T> HashMap<Integer, String> createDatabaseObjects(
            SR sourceRepo,
            TR targetRepo,
            Function<S, Integer> getSourceId,
            Function<T, String> getTargetId,
            Function<S, T> map) {

        val sourceObjects = sourceRepo.findAll();
        val csvIdToNewId = new HashMap<Integer, String>();
        for (val sourceObject : sourceObjects) {
            val targetObject = targetRepo.save(map.apply(sourceObject));
            csvIdToNewId.put(getSourceId.apply(sourceObject), getTargetId.apply(targetObject));
        }
        return csvIdToNewId;
    }

    private static ConversionFactor map(ConversionFactorCsv csv) {
        return null;
    }

    private static Food map(FoodCsv csv) {
        return null;
    }

    private static FoodGroup map(FoodGroupCsv csv) {
        return null;
    }

    private static FoodSource map(FoodSourceCsv csv) {
        return null;
    }

    private static MeasureName map(MeasureNameCsv csv) {
        val measureName = new MeasureName();
        measureName.setMeasureDescription(csv.getMeasureDescription());
        measureName.setMeasureDescriptionF(csv.getMeasureDescriptionF());
        return measureName;
    }

    private static NutrientAmount map(NutrientAmountCsv csv) {
        return null;
    }

    private static NutrientName map(NutrientNameCsv csv) {
        val nutrientName = new NutrientName();
        nutrientName.setNutrientCode(csv.getNutrientCode());
        nutrientName.setNutrientSymbol(csv.getNutrientSymbol());
        nutrientName.setNutrientUnit(csv.getNutrientUnit());
        nutrientName.setNutrientName(csv.getNutrientName());
        nutrientName.setNutrientNameF(csv.getNutrientNameF());
        nutrientName.setTagName(csv.getTagName());
        nutrientName.setNutrientDecimals(csv.getNutrientDecimals());
        return nutrientName;
    }

    private static NutrientSource map(NutrientSourceCsv csv) {
        return null;
    }

    private static RefuseAmount map(RefuseAmountCsv csv) {
        return null;
    }

    private static RefuseName map(RefuseNameCsv csv) {
        val refuseName = new RefuseName();
        refuseName.setRefuseDescription(csv.getRefuseDescription());
        refuseName.setRefuseDescriptionF(csv.getRefuseDescriptionF());
        return refuseName;
    }

    private static YieldAmount map(YieldAmountCsv csv) {
        return null;
    }

    private static YieldName map(YieldNameCsv csv) {
        val yieldName = new YieldName();
        yieldName.setYieldDescription(csv.getYieldDescription());
        yieldName.setYieldDescriptionF(csv.getYieldDescriptionF());
        return yieldName;
    }
}
