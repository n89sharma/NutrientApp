package nutrientapp.fooditem;

import lombok.val;
import nutrientapp.domain.databaseobjects.ConversionFactor;
import nutrientapp.domain.databaseobjects.MeasureName;
import nutrientapp.domain.internal.Food;
import nutrientapp.domain.internal.FoodSummary;
import nutrientapp.domain.internal.MacroNutrients;
import nutrientapp.domain.internal.Measure;
import nutrientapp.domain.internal.MicroNutrients;
import nutrientapp.domain.internal.Nutrient;
import nutrientapp.domain.repositories.ConversionFactorRepository;
import nutrientapp.domain.repositories.FoodRepository;
import nutrientapp.domain.repositories.MeasureNameRepository;
import nutrientapp.nutrient.NutrientCodes;
import nutrientapp.nutrient.NutrientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static nutrientapp.nutrient.NutrientCodes.*;

@Service
public class FoodItemService {
    private ConversionFactorRepository conversionFactorRepository;
    private FoodRepository foodRepository;
    private MeasureNameRepository measureNameRepository;
    private NutrientService nutrientService;

    @Autowired
    public FoodItemService(
        FoodRepository foodRepository,
        ConversionFactorRepository conversionFactorRepository,
        MeasureNameRepository measureNameRepository,
        NutrientService nutrientService) {

        this.conversionFactorRepository = conversionFactorRepository;
        this.foodRepository = foodRepository;
        this.measureNameRepository = measureNameRepository;
        this.nutrientService = nutrientService;
    }

    public Food getFoodItem(String foodId, String measureId, double serving) {
        val conversionFactor = conversionFactorRepository.findByFoodIdAndMeasureId(foodId, measureId);
        val totalConversionFactor = conversionFactor.getConversionFactorValue()*serving;
        val foodItem = getFoodItem(foodId);
        foodItem.multiplyByFactor(totalConversionFactor);
        return foodItem;
    }

    Food getFoodItem(String foodId) {
        val nutrients = nutrientService
                .getNutrients(foodId)
                .stream()
            .collect(toMap(Nutrient::getNutrientCode, identity()));

        val minerals = new MicroNutrients.Minerals();
        minerals.setSodium(getNutrientOrEmpty(nutrients, SODIUM));
        minerals.setIron(getNutrientOrEmpty(nutrients, IRON));
        minerals.setCalcium(getNutrientOrEmpty(nutrients, CALCIUM));
        minerals.setMagnesium(getNutrientOrEmpty(nutrients, MAGNESIUM));
        minerals.setPotassium(getNutrientOrEmpty(nutrients, POTASSIUM));
        minerals.setZinc(getNutrientOrEmpty(nutrients, ZINC));

        val vitamins = new MicroNutrients.Vitamins();
        vitamins.setVitaminA(getVitaminA(nutrients));
        vitamins.setVitaminB6(getNutrientOrEmpty(nutrients, VITAMIN_B6));
        vitamins.setVitaminB12(getNutrientOrEmpty(nutrients, VITAMIN_B12));
        vitamins.setVitaminC(getNutrientOrEmpty(nutrients, VITAMIN_C));
        vitamins.setVitaminD(getNutrientOrEmpty(nutrients, VITAMIN_D));

        val essentialFats = new MicroNutrients.EssentialFats();
        essentialFats.setOmega3(getNutrientOrEmpty(nutrients, OMEGA3));
        essentialFats.setOmega6(getNutrientOrEmpty(nutrients, OMEGA6));

        val microNutrients = new MicroNutrients(minerals, vitamins, essentialFats);

        val macroNutrients = new MacroNutrients();
        macroNutrients.setProtein(getNutrientOrEmpty(nutrients, PROTEIN));
        macroNutrients.setCarbohydrates(getNutrientOrEmpty(nutrients, CARBOHYDRATES));
        macroNutrients.setSugars(getNutrientOrEmpty(nutrients, SUGARS));
        macroNutrients.setFibre(getNutrientOrEmpty(nutrients, FIBRE));
        macroNutrients.setFats(getNutrientOrEmpty(nutrients, FAT));
        macroNutrients.setSaturatedFats(getNutrientOrEmpty(nutrients, SATURATED_FAT));
        macroNutrients.setTransFats(getNutrientOrEmpty(nutrients, TRANS_FAT));
        macroNutrients.setCholesterol(getNutrientOrEmpty(nutrients, CHOLESTEROL));

        val calorieNutrient = getNutrientOrEmpty(nutrients, CALORIES);
        val dbFood = foodRepository.findOne(foodId);
        return new Food(
                dbFood.getFoodId(),
                dbFood.getFoodDescription(),
                calorieNutrient.getAmountValue(),
                macroNutrients,
                microNutrients);
    }

    private Nutrient getVitaminA(Map<Integer, Nutrient> nutrients) {
        val retinol = getNutrientOrEmpty(nutrients, RETINOL);
        val betaCarotene = getNutrientOrEmpty(nutrients, BETA_CAROTENE);
        val alphaCarotene = getNutrientOrEmpty(nutrients, ALPHA_CAROTENE);
        val betaCryptoxanthin = getNutrientOrEmpty(nutrients, BETA_CRYPTOXANTHIN);
        val retinolActivityEquivalent = retinol.getAmountValue() +
                betaCarotene.getAmountValue() / 12 +
                alphaCarotene.getAmountValue() / 24 +
                betaCryptoxanthin.getAmountValue() / 24;

        val nutrient = new Nutrient();
        nutrient.setName("Vitamin A");
        nutrient.setSymbol("RAE");
        nutrient.setUnit("RAE");
        nutrient.setAmountValue(retinolActivityEquivalent);
        return nutrient;
    }

    private Nutrient getNutrientOrEmpty(Map<Integer, Nutrient> nutrients, NutrientCodes nutrientCode) {
        return nutrients.getOrDefault(
                nutrientCode.getNutrientCode(),
                nutrientService.getEmptyNutrient(nutrientCode.getNutrientCode()));
    }

    public List<FoodSummary> getFoodItems() {
        val foodSummaries = new ArrayList<FoodSummary>();
        val foodItems = foodRepository.findAll();
        for (val foodItem : foodItems) {
            val foodSummary = new FoodSummary();
            foodSummary.setFoodId(foodItem.getFoodId());
            foodSummary.setFoodDescription(foodItem.getFoodDescription());
            foodSummary.setFoodDescriptionF(foodItem.getFoodDescriptionF());
            foodSummaries.add(foodSummary);
        }
        return foodSummaries;
    }

    public List<Measure> getMeasures(String foodId) {
        val measures = new ArrayList<Measure>();
        val dbConversionFactors = conversionFactorRepository.findByFoodId(foodId);
        for (val dbConversionFactor : dbConversionFactors) {
            val dbMeasureNames = measureNameRepository.findOne(dbConversionFactor.getMeasureId());
            if (dbMeasureNames != null) {
                measures.add(mapMeasure(dbMeasureNames, dbConversionFactor));
            }
        }
        return measures;
    }

    public Measure getMeasure(String foodId, String measureId) {
        val conversionFactorCsv = conversionFactorRepository.findByFoodIdAndMeasureId(foodId, measureId);
        val measureNameCsv = measureNameRepository.findOne(conversionFactorCsv.getMeasureId());
        return mapMeasure(measureNameCsv, conversionFactorCsv);
    }

    private Measure mapMeasure(MeasureName measureName, ConversionFactor conversionFactor) {
        val measure = new Measure();
        measure.setMeasureId(measureName.getMeasureId());
        measure.setConversionFactor(conversionFactor.getConversionFactorValue());
        measure.setMeasureName(measureName.getMeasureDescription());
        measure.setMeasureNameF(measureName.getMeasureDescriptionF());
        return measure;
    }
}