import lombok.val;
import nutrientapp.NutrientApp;
import nutrientapp.fooditem.FoodItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NutrientApp.class)
public class FoodItemServiceTest {

    @Autowired
    private FoodItemService foodItemService;

    @Test
    public void foodSummaryRequested_foodSummaryRetrieved() {
        val cakeId = "5e36f2cdc59e0611f2ec0c33";
        val cakeFoodItem = foodItemService.getFoodItem(cakeId);

        assertThat(cakeFoodItem).isNotNull();
        assertThat(cakeFoodItem.getFoodDescription()).isEqualTo("Coffee cake, cheese");
        assertThat(cakeFoodItem.getCalories()).isEqualTo(339.0);

        val macro = cakeFoodItem.getMacroNutrients();
        assertThat(macro.getCarbohydrates().getAmountValue()).isEqualTo(44.3);
        assertThat(macro.getFats().getAmountValue()).isEqualTo(15.2);
        assertThat(macro.getProtein().getAmountValue()).isEqualTo(7.0);
        assertThat(macro.getSugars().getAmountValue()).isEqualTo(0.0);
        assertThat(macro.getFibre().getAmountValue()).isEqualTo(1.0);
        assertThat(macro.getSaturatedFats().getAmountValue()).isEqualTo(5.391);
        assertThat(macro.getTransFats().getAmountValue()).isEqualTo(1.612);
        assertThat(macro.getCholesterol().getAmountValue()).isEqualTo(85.0);

        val vitamins = cakeFoodItem.getMicroNutrients().getVitamins();
        assertThat(vitamins.getVitaminA().getAmountValue()).isEqualTo(86.0);
        assertThat(vitamins.getVitaminB6().getAmountValue()).isEqualTo(0.058);
        assertThat(vitamins.getVitaminB12().getAmountValue()).isEqualTo(0.34);
        assertThat(vitamins.getVitaminC().getAmountValue()).isEqualTo(0.1);
        assertThat(vitamins.getVitaminD().getAmountValue()).isEqualTo(13.0);

        val minerals = cakeFoodItem.getMicroNutrients().getMinerals();
        assertThat(minerals.getCalcium().getAmountValue()).isEqualTo(59.0);
        assertThat(minerals.getIron().getAmountValue()).isEqualTo(0.64);
        assertThat(minerals.getMagnesium().getAmountValue()).isEqualTo(15.0);
        assertThat(minerals.getPotassium().getAmountValue()).isEqualTo(289.0);
        assertThat(minerals.getSodium().getAmountValue()).isEqualTo(339.0);
        assertThat(minerals.getZinc().getAmountValue()).isEqualTo(0.59);

        val essentialFats = cakeFoodItem.getMicroNutrients().getEssentialFats();
        assertThat(essentialFats.getOmega3().getAmountValue()).isEqualTo(0.0);
        assertThat(essentialFats.getOmega6().getAmountValue()).isEqualTo(0.0);
    }
}
