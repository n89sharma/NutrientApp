import lombok.val;
import nutrientapp.NutrientApp;
import nutrientapp.domain.csvobjects.ConversionFactorCsv;
import nutrientapp.domain.csvobjects.FoodCsv;
import nutrientapp.domain.csvobjects.FoodGroupCsv;
import nutrientapp.domain.csvobjects.FoodSourceCsv;
import nutrientapp.domain.csvobjects.MeasureNameCsv;
import nutrientapp.domain.csvobjects.NutrientAmountCsv;
import nutrientapp.domain.csvobjects.NutrientNameCsv;
import nutrientapp.domain.csvobjects.NutrientSourceCsv;
import nutrientapp.domain.csvobjects.RefuseAmountCsv;
import nutrientapp.domain.csvobjects.RefuseNameCsv;
import nutrientapp.domain.csvobjects.YieldAmountCsv;
import nutrientapp.domain.csvobjects.YieldNameCsv;
import nutrientapp.domain.csvrepositories.ConversionFactorCsvRepository;
import nutrientapp.domain.csvrepositories.FoodCsvRepository;
import nutrientapp.domain.csvrepositories.FoodGroupCsvRepository;
import nutrientapp.domain.csvrepositories.FoodSourceCsvRepository;
import nutrientapp.domain.csvrepositories.MeasureNameCsvRepository;
import nutrientapp.domain.csvrepositories.NutrientAmountCsvRepository;
import nutrientapp.domain.csvrepositories.NutrientNameCsvRepository;
import nutrientapp.domain.csvrepositories.NutrientSourceCsvRepository;
import nutrientapp.domain.csvrepositories.RefuseAmountCsvRepository;
import nutrientapp.domain.csvrepositories.RefuseNameCsvRepository;
import nutrientapp.domain.csvrepositories.YieldAmountCsvRepository;
import nutrientapp.domain.csvrepositories.YieldNameCsvRepository;
import nutrientapp.domain.databaseobjects.DbConversionFactor;
import nutrientapp.domain.databaseobjects.DbFood;
import nutrientapp.domain.databaseobjects.DbFoodGroup;
import nutrientapp.domain.databaseobjects.DbFoodSource;
import nutrientapp.domain.databaseobjects.DbMeasureName;
import nutrientapp.domain.databaseobjects.DbNutrientAmount;
import nutrientapp.domain.databaseobjects.DbNutrientName;
import nutrientapp.domain.databaseobjects.DbNutrientSource;
import nutrientapp.domain.databaseobjects.DbRefuseAmount;
import nutrientapp.domain.databaseobjects.DbRefuseName;
import nutrientapp.domain.databaseobjects.DbYieldAmount;
import nutrientapp.domain.databaseobjects.DbYieldName;
import nutrientapp.utils.CsvFiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import static nutrientapp.utils.CsvFiles.CONVERSION_FACTOR;
import static nutrientapp.utils.CsvFiles.FOOD_GROUP;
import static nutrientapp.utils.CsvFiles.FOOD_NAME;
import static nutrientapp.utils.CsvFiles.FOOD_SOURCE;
import static nutrientapp.utils.CsvFiles.MEASURE_NAME;
import static nutrientapp.utils.CsvFiles.NUTRIENT_AMOUNT;
import static nutrientapp.utils.CsvFiles.NUTRIENT_NAME;
import static nutrientapp.utils.CsvFiles.NUTRIENT_SOURCE;
import static nutrientapp.utils.CsvFiles.REFUSE_AMOUNT;
import static nutrientapp.utils.CsvFiles.REFUSE_NAME;
import static nutrientapp.utils.CsvFiles.YIELD_AMOUNT;
import static nutrientapp.utils.CsvFiles.YIELD_NAME;
import static nutrientapp.utils.CsvFiles.getAllTableRowsFrom;

public class DatabaseInitialization {

    @Autowired
    ConversionFactorCsvRepository conversionFactorCsvRepository;
    @Autowired
    FoodGroupCsvRepository foodGroupCsvRepository;
    @Autowired
    FoodCsvRepository foodCsvRepository;
    @Autowired
    FoodSourceCsvRepository foodSourceCsvRepository;
    @Autowired
    MeasureNameCsvRepository measureNameCsvRepository;
    @Autowired
    NutrientAmountCsvRepository nutrientAmountCsvRepository;
    @Autowired
    NutrientNameCsvRepository nutrientNameCsvRepository;
    @Autowired
    NutrientSourceCsvRepository nutrientSourceCsvRepository;
    @Autowired
    RefuseAmountCsvRepository refuseAmountCsvRepository;
    @Autowired
    RefuseNameCsvRepository refuseNameCsvRepository;
    @Autowired
    YieldNameCsvRepository yieldNameCsvRepository;
    @Autowired
    YieldAmountCsvRepository yieldAmountCsvRepository;

    @Autowired
    nutrientapp.domain.repositories.ConversionFactorRepository conversionFactorRepository;
    @Autowired
    nutrientapp.domain.repositories.FoodGroupRepository foodGroupRepository;
    @Autowired
    nutrientapp.domain.repositories.FoodRepository foodRepository;
    @Autowired
    nutrientapp.domain.repositories.FoodSourceRepository foodSourceRepository;
    @Autowired
    nutrientapp.domain.repositories.MeasureNameRepository measureNameRepository;
    @Autowired
    nutrientapp.domain.repositories.NutrientAmountRepository nutrientAmountRepository;
    @Autowired
    nutrientapp.domain.repositories.NutrientNameRepository nutrientNameRepository;
    @Autowired
    nutrientapp.domain.repositories.NutrientSourceRepository nutrientSourceRepository;
    @Autowired
    nutrientapp.domain.repositories.RefuseAmountRepository refuseAmountRepository;
    @Autowired
    nutrientapp.domain.repositories.RefuseNameRepository refuseNameRepository;
    @Autowired
    nutrientapp.domain.repositories.YieldNameRepository yieldNameRepository;
    @Autowired
    nutrientapp.domain.repositories.YieldAmountRepository yieldAmountRepository;

    public void createNewDatabaseObjects() throws IOException {
        val yieldNameMap = createDatabaseObjects(
            YIELD_NAME,
            yieldNameCsvRepository,
            yieldNameRepository,
            YieldNameCsv::getYieldId,
            DbYieldName::getYieldId,
            DatabaseInitialization::mapYieldName);

        val refuseNameMap = createDatabaseObjects(
            REFUSE_NAME,
            refuseNameCsvRepository,
            refuseNameRepository,
            RefuseNameCsv::getRefuseId,
            DbRefuseName::getRefuseId,
            DatabaseInitialization::mapRefuseName);

        val measureNameMap = createDatabaseObjects(
            MEASURE_NAME,
            measureNameCsvRepository,
            measureNameRepository,
            MeasureNameCsv::getMeasureId,
            DbMeasureName::getMeasureId,
            DatabaseInitialization::mapMeasureName);

        val nutrientNameMap = createDatabaseObjects(
            NUTRIENT_NAME,
            nutrientNameCsvRepository,
            nutrientNameRepository,
            NutrientNameCsv::getNutrientNameId,
            DbNutrientName::getNutrientNameId,
            DatabaseInitialization::mapNutrientName);

        val nutrientSourceMap = createDatabaseObjects(
            NUTRIENT_SOURCE,
            nutrientSourceCsvRepository,
            nutrientSourceRepository,
            NutrientSourceCsv::getNutrientSourceId,
            DbNutrientSource::getNutrientSourceId,
            DatabaseInitialization::mapNutrientSource);

        val foodSourceMap = createDatabaseObjects(
            FOOD_SOURCE,
            foodSourceCsvRepository,
            foodSourceRepository,
            FoodSourceCsv::getFoodSourceId,
            DbFoodSource::getFoodSourceId,
            DatabaseInitialization::mapFoodSource);

        val foodGroupMap = createDatabaseObjects(
            FOOD_GROUP,
            foodGroupCsvRepository,
            foodGroupRepository,
            FoodGroupCsv::getFoodGroupId,
            DbFoodGroup::getFoodGroupId,
            DatabaseInitialization::mapFoodGroup);

        //food name
        foodRepository.deleteAll();
        val foodCsvs = (List<FoodCsv>) getAllTableRowsFrom(FOOD_NAME);
        val foodMap = new HashMap<Integer, String>();
        for (val foodCsv : foodCsvs) {
            val food = foodRepository.save(mapFood(
                foodCsv,
                foodGroupMap.get(foodCsv.getFoodGroupId()),
                foodSourceMap.get(foodCsv.getFoodSourceId())));
            foodMap.put(foodCsv.getFoodId(), food.getFoodId());
        }

        //yield amount
        yieldAmountRepository.deleteAll();
        val yieldAmountCsvs = (List<YieldAmountCsv>) getAllTableRowsFrom(YIELD_AMOUNT);
        for (val yieldAmountCsv : yieldAmountCsvs) {
            yieldAmountRepository.save(mapYieldAmount(
                yieldAmountCsv,
                foodMap.get(yieldAmountCsv.getFoodId()),
                yieldNameMap.get(yieldAmountCsv.getYieldId())));
        }

        //refuse amount
        refuseAmountRepository.deleteAll();
        val refuseAmountCsvs = (List<RefuseAmountCsv>) getAllTableRowsFrom(REFUSE_AMOUNT);
        for (val refuseAmountCsv : refuseAmountCsvs) {
            refuseAmountRepository.save(mapRefuseAmount(
                refuseAmountCsv,
                foodMap.get(refuseAmountCsv.getFoodId()),
                refuseNameMap.get(refuseAmountCsv.getRefuseId())));
        }

        //conversion factor
        conversionFactorRepository.deleteAll();
        val conversionFactorCsvs = (List<ConversionFactorCsv>) getAllTableRowsFrom(CONVERSION_FACTOR);
        for (val conversionFactorCsv : conversionFactorCsvs) {
            conversionFactorRepository.save(mapConversionFactor(
                conversionFactorCsv,
                foodMap.get(conversionFactorCsv.getFoodId()),
                measureNameMap.get(conversionFactorCsv.getMeasureId())));
        }

        //nutrient amount
        nutrientAmountRepository.deleteAll();
        val nutrientAmountCsvs = (List<NutrientAmountCsv>) getAllTableRowsFrom(NUTRIENT_AMOUNT);
        for (val nutrientAmountCsv : nutrientAmountCsvs) {
            nutrientAmountRepository.save(mapNutrientAmount(
                nutrientAmountCsv,
                foodMap.get(nutrientAmountCsv.getFoodId()),
                nutrientNameMap.get(nutrientAmountCsv.getNutrientNameId()),
                nutrientSourceMap.get(nutrientAmountCsv.getNutrientSourceId())));
        }
    }

    private <SR extends MongoRepository<S, String>, S, TR extends MongoRepository<T, String>, T> HashMap<Integer, String> createDatabaseObjects(
        CsvFiles csvFile,
        SR sourceRepo,
        TR targetRepo,
        Function<S, Integer> getSourceId,
        Function<T, String> getTargetId,
        Function<S, T> map) throws IOException {

        sourceRepo.deleteAll();
        val sourceObjects = (List<S>) getAllTableRowsFrom(csvFile);
        val csvIdToNewId = new HashMap<Integer, String>();
        for (val sourceObject : sourceObjects) {
            val targetObject = targetRepo.save(map.apply(sourceObject));
            csvIdToNewId.put(getSourceId.apply(sourceObject), getTargetId.apply(targetObject));
        }
        System.out.println(String.format("source objects: %d", sourceObjects.size()));
        System.out.println(String.format("target objects: %d", csvIdToNewId.size()));
        return csvIdToNewId;
    }

    private static DbConversionFactor mapConversionFactor(ConversionFactorCsv csv, String foodId, String measureId) {
        val conversionFactor = new DbConversionFactor();
        conversionFactor.setFoodId(foodId);
        conversionFactor.setMeasureId(measureId);
        conversionFactor.setConversionFactorValue(csv.getConversionFactorValue());
        conversionFactor.setConversionFactorDateOfEntry(csv.getConversionFactorDateOfEntry());
        return conversionFactor;
    }

    private static DbFood mapFood(FoodCsv csv, String foodGroupId, String foodSourceId) {
        val food = new DbFood();
        food.setFoodCode(csv.getFoodCode());
        food.setFoodGroupId(foodGroupId);
        food.setFoodSourceId(foodSourceId);
        food.setFoodDescription(csv.getFoodDescription());
        food.setFoodDescriptionF(csv.getFoodDescriptionF());
        food.setCountryCode(csv.getCountryCode());
        food.setFoodDateOfEntry(csv.getFoodDateOfEntry());
        food.setFoodDateOfPublication(csv.getFoodDateOfPublication());
        food.setScientificName(csv.getScientificName());
        return food;
    }

    private static DbFoodGroup mapFoodGroup(FoodGroupCsv csv) {
        val foodGroup = new DbFoodGroup();
        foodGroup.setFoodGroupCode(csv.getFoodGroupCode());
        foodGroup.setFoodGroupName(csv.getFoodGroupName());
        foodGroup.setFoodGroupNameF(csv.getFoodGroupNameF());
        return foodGroup;
    }

    private static DbFoodSource mapFoodSource(FoodSourceCsv csv) {
        val foodSource = new DbFoodSource();
        foodSource.setFoodSourceCode(csv.getFoodSourceCode());
        foodSource.setFoodSourceDescription(csv.getFoodSourceDescription());
        foodSource.setFoodSourceDescriptionF(csv.getFoodSourceDescriptionF());
        return foodSource;
    }

    private static DbMeasureName mapMeasureName(MeasureNameCsv csv) {
        val measureName = new DbMeasureName();
        measureName.setMeasureDescription(csv.getMeasureDescription());
        measureName.setMeasureDescriptionF(csv.getMeasureDescriptionF());
        return measureName;
    }

    private static DbNutrientAmount mapNutrientAmount(
        NutrientAmountCsv csv,
        String foodId,
        String nutrientNameId,
        String nutrientSourceId) {

        val nutrientAmount = new DbNutrientAmount();
        nutrientAmount.setFoodId(foodId);
        nutrientAmount.setNutrientNameId(nutrientNameId);
        nutrientAmount.setNutrientSourceId(nutrientSourceId);
        nutrientAmount.setNutrientValue(csv.getNutrientValue());
        nutrientAmount.setStandardError(csv.getStandardError());
        nutrientAmount.setNumberOfObservations(csv.getNumberOfObservations());
        nutrientAmount.setNutrientDateOfEntry(csv.getNutrientDateOfEntry());
        return nutrientAmount;
    }

    private static DbNutrientName mapNutrientName(NutrientNameCsv csv) {
        val nutrientName = new DbNutrientName();
        nutrientName.setNutrientCode(csv.getNutrientCode());
        nutrientName.setNutrientSymbol(csv.getNutrientSymbol());
        nutrientName.setNutrientUnit(csv.getNutrientUnit());
        nutrientName.setNutrientName(csv.getNutrientName());
        nutrientName.setNutrientNameF(csv.getNutrientNameF());
        nutrientName.setTagName(csv.getTagName());
        nutrientName.setNutrientDecimals(csv.getNutrientDecimals());
        return nutrientName;
    }

    private static DbNutrientSource mapNutrientSource(NutrientSourceCsv csv) {
        val nutrientSource = new DbNutrientSource();
        nutrientSource.setNutrientSourceCode(csv.getNutrientSourceCode());
        nutrientSource.setNutrientSourceDescription(csv.getNutrientSourceDescription());
        nutrientSource.setNutrientSourceDescriptionF(csv.getNutrientSourceDescriptionF());
        return nutrientSource;
    }

    private static DbRefuseAmount mapRefuseAmount(RefuseAmountCsv csv, String foodId, String refuseId) {
        val refuseAmount = new DbRefuseAmount();
        refuseAmount.setFoodId(foodId);
        refuseAmount.setRefuseId(refuseId);
        refuseAmount.setRefuseAmount(csv.getRefuseAmount());
        refuseAmount.setRefuseDateOfEntry(csv.getRefuseDateOfEntry());
        return refuseAmount;
    }

    private static DbRefuseName mapRefuseName(RefuseNameCsv csv) {
        val refuseName = new DbRefuseName();
        refuseName.setRefuseDescription(csv.getRefuseDescription());
        refuseName.setRefuseDescriptionF(csv.getRefuseDescriptionF());
        return refuseName;
    }

    private static DbYieldAmount mapYieldAmount(YieldAmountCsv csv, String foodId, String yieldId) {
        val yieldAmount = new DbYieldAmount();
        yieldAmount.setFoodId(foodId);
        yieldAmount.setYieldId(yieldId);
        yieldAmount.setYieldAmount(csv.getYieldAmount());
        yieldAmount.setYieldDateOfEntry(csv.getYieldDateOfEntry());
        return yieldAmount;
    }

    private static DbYieldName mapYieldName(YieldNameCsv csv) {
        val yieldName = new DbYieldName();
        yieldName.setYieldDescription(csv.getYieldDescription());
        yieldName.setYieldDescriptionF(csv.getYieldDescriptionF());
        return yieldName;
    }
}
