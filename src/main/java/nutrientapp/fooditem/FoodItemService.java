package nutrientapp.fooditem;

import lombok.val;
import nutrientapp.domain.repositories.FoodRepository;
import nutrientapp.nutrient.MacroNutrients;
import nutrientapp.nutrient.MicroNutrients;
import nutrientapp.nutrient.Nutrient;
import nutrientapp.nutrient.NutrientIds;
import nutrientapp.nutrient.NutrientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static nutrientapp.nutrient.NutrientIds.BETA_CAROTENE;
import static nutrientapp.nutrient.NutrientIds.CALCIUM;
import static nutrientapp.nutrient.NutrientIds.CALORIES;
import static nutrientapp.nutrient.NutrientIds.CARBOHYDRATES;
import static nutrientapp.nutrient.NutrientIds.CHOLESTEROL;
import static nutrientapp.nutrient.NutrientIds.FAT;
import static nutrientapp.nutrient.NutrientIds.FIBRE;
import static nutrientapp.nutrient.NutrientIds.IRON;
import static nutrientapp.nutrient.NutrientIds.MAGNESIUM;
import static nutrientapp.nutrient.NutrientIds.OMEGA3;
import static nutrientapp.nutrient.NutrientIds.OMEGA6;
import static nutrientapp.nutrient.NutrientIds.POTASSIUM;
import static nutrientapp.nutrient.NutrientIds.PROTEIN;
import static nutrientapp.nutrient.NutrientIds.RETINOL;
import static nutrientapp.nutrient.NutrientIds.SATURATED_FAT;
import static nutrientapp.nutrient.NutrientIds.SODIUM;
import static nutrientapp.nutrient.NutrientIds.SUGARS;
import static nutrientapp.nutrient.NutrientIds.TRANS_FAT;
import static nutrientapp.nutrient.NutrientIds.VITAMIN_B12;
import static nutrientapp.nutrient.NutrientIds.VITAMIN_B6;
import static nutrientapp.nutrient.NutrientIds.VITAMIN_C;
import static nutrientapp.nutrient.NutrientIds.VITAMIN_D;
import static nutrientapp.nutrient.NutrientIds.ZINC;

@Service
public class FoodItemService {
    private FoodRepository foodRepository;
    private NutrientService nutrientService;

    @Autowired
    public FoodItemService(FoodRepository foodRepository, NutrientService nutrientService) {
        this.foodRepository = foodRepository;
        this.nutrientService = nutrientService;
    }

    Food getFoodItem(int foodId) {
        val nutrients = nutrientService
                .getNutrients(foodId)
                .stream()
                .collect(toMap(Nutrient::getNutrientNameId, Function.identity()));

        val minerals = new MicroNutrients.Minerals();
        minerals.setSodium(getNutrientOrEmpty(nutrients, SODIUM));
        minerals.setIron(getNutrientOrEmpty(nutrients, IRON));
        minerals.setCalcium(getNutrientOrEmpty(nutrients, CALCIUM));
        minerals.setMagnesium(getNutrientOrEmpty(nutrients, MAGNESIUM));
        minerals.setPotassium(getNutrientOrEmpty(nutrients, POTASSIUM));
        minerals.setZinc(getNutrientOrEmpty(nutrients, ZINC));

        val retinol = getNutrientOrEmpty(nutrients, RETINOL);
        val betaCarotene = getNutrientOrEmpty(nutrients, BETA_CAROTENE);
        val vitamins = new MicroNutrients.Vitamins();
        vitamins.setVitaminA(getNutrientOrEmpty(nutrients, RETINOL)); //TODO: change to consistent units.
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

    private Nutrient getNutrientOrEmpty(Map<Integer, Nutrient> nutrients, NutrientIds nutrientId) {
        return nutrients.getOrDefault(
                nutrientId.getId(),
                nutrientService.getEmptyNutrient(nutrientId.getId()));
    }
}