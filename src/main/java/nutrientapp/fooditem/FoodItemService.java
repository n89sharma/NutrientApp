package nutrientapp.fooditem;

import lombok.val;
import nutrientapp.domain.csvobjects.ConversionFactorCsv;
import nutrientapp.domain.csvobjects.MeasureNameCsv;
import nutrientapp.domain.repositories.ConversionFactorRepository;
import nutrientapp.domain.repositories.FoodRepository;
import nutrientapp.domain.repositories.MeasureNameRepository;
import nutrientapp.nutrient.MacroNutrients;
import nutrientapp.nutrient.MicroNutrients;
import nutrientapp.nutrient.Nutrient;
import nutrientapp.nutrient.NutrientIds;
import nutrientapp.nutrient.NutrientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static nutrientapp.nutrient.NutrientIds.*;

@Service
public class FoodItemService {
    private ConversionFactorRepository conversionFactorRepository;
    private FoodRepository foodRepository;
    private MeasureNameRepository measureNameRepository;
    private NutrientService nutrientService;

    @Autowired
    public FoodItemService(
        ConversionFactorRepository conversionFactorRepository,
        FoodRepository foodRepository,
        MeasureNameRepository measureNameRepository,
        NutrientService nutrientService) {

        this.conversionFactorRepository = conversionFactorRepository;
        this.foodRepository = foodRepository;
        this.measureNameRepository = measureNameRepository;
        this.nutrientService = nutrientService;
    }

    public Food getFoodItem(int foodId, int measureId, double serving) {
        val conversionFactor = conversionFactorRepository.findByFoodIdAndMeasureId(foodId, measureId);
        val totalConversionFactor = conversionFactor.getConversionFactorValue()*serving;
        val foodItem = getFoodItem(foodId);
        foodItem.multiplyByFactor(totalConversionFactor);
        return foodItem;
    }

    Food getFoodItem(int foodId) {
        val nutrients = nutrientService
                .getNutrients(foodId)
                .stream()
            .collect(toMap(Nutrient::getNutrientNameId, identity()));

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
        val dbFood = foodRepository.findByFoodId(foodId);
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

    private Nutrient getNutrientOrEmpty(Map<Integer, Nutrient> nutrients, NutrientIds nutrientId) {
        return nutrients.getOrDefault(
                nutrientId.getId(),
                nutrientService.getEmptyNutrient(nutrientId.getId()));
    }

    public List<FoodSummary> getFoodItems() {
        val foodSummaries = new ArrayList<FoodSummary>();
        val csvFoodItems = foodRepository.findAll();
        for (val csvFoodItem : csvFoodItems) {
            val foodSummary = new FoodSummary();
            foodSummary.setFoodId(csvFoodItem.getFoodId());
            foodSummary.setFoodDescription(csvFoodItem.getFoodDescription());
            foodSummary.setFoodDescriptionF(csvFoodItem.getFoodDescriptionF());
            foodSummaries.add(foodSummary);
        }
        return foodSummaries;
    }

    public List<Measure> getMeasures(int foodId) {
        val measures = new ArrayList<Measure>();
        val conversionFactorCsvs = conversionFactorRepository.findByFoodId(foodId);
        for (val conversionFactorCsv : conversionFactorCsvs) {
            val measureNameCsv = measureNameRepository.findByMeasureId(conversionFactorCsv.getMeasureId());
            if (measureNameCsv != null) {
                measures.add(mapMeasure(measureNameCsv, conversionFactorCsv));
            }
        }
        return measures;
    }

    public Measure getMeasure(int foodId, int measureId) {
        val conversionFactorCsv = conversionFactorRepository.findByFoodIdAndMeasureId(foodId, measureId);
        val measureNameCsv = measureNameRepository.findByMeasureId(conversionFactorCsv.getMeasureId());
        return mapMeasure(measureNameCsv, conversionFactorCsv);
    }

    private Measure mapMeasure(MeasureNameCsv measureNameCsv, ConversionFactorCsv conversionFactorCsv) {
        val measure = new Measure();
        measure.setMeasureId(measureNameCsv.getMeasureId());
        measure.setConversionFactor(conversionFactorCsv.getConversionFactorValue());
        measure.setMeasureName(measureNameCsv.getMeasureDescription());
        measure.setMeasureNameF(measureNameCsv.getMeasureDescriptionF());
        return measure;
    }
}