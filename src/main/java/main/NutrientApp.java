package main;

import dbobjects.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static dbobjects.Tables.*;
import static java.util.stream.Collectors.toMap;

@Controller
@EnableAutoConfiguration
public class NutrientApp {

    private List<FoodName> foodNames = getAllTableRowsFrom(FOOD_NAME);
    private List<NutrientName> nutrientNames = getAllTableRowsFrom(NUTRIENT_NAME);
    private List<NutrientAmount> nutrientAmounts = getAllTableRowsFrom(NUTRIENT_AMOUNT);
    private Map<Integer, FoodName> foodNamesPerFoodId = getMap(foodNames, FoodName::getFoodId);
    private Map<Integer, NutrientName> nutrientNamesPerNutrientId = getMap(nutrientNames, NutrientName::getNutrientNameId);
    private Map<Integer, List<NutrientAmount>> nutrientAmountsPerFoodId = nutrientAmounts
        .stream()
        .collect(Collectors.groupingBy(NutrientAmount::getFoodId, Collectors.toList()));

    public static void main(String[] args) throws IOException {

        SpringApplication.run(NutrientApp.class, args);

        //@formatter:off
        List<ConversionFactor> conversionFactors    = getAllTableRowsFrom(CONVERSION_FACTOR);
        List<FoodGroup> foodGroups                  = getAllTableRowsFrom(FOOD_GROUP);
        List<FoodSource> foodSources                = getAllTableRowsFrom(FOOD_SOURCE);
        List<MeasureName> measureNames              = getAllTableRowsFrom(MEASURE_NAME);
        List<NutrientSource> nutrientSources        = getAllTableRowsFrom(NUTRIENT_SOURCE);
        List<RefuseAmount> refuseAmounts            = getAllTableRowsFrom(REFUSE_AMOUNT);
        List<RefuseName> refuseNames                = getAllTableRowsFrom(REFUSE_NAME);
        List<YieldAmount> yieldAmounts              = getAllTableRowsFrom(YIELD_AMOUNT);
        List<YieldName> yieldNames                  = getAllTableRowsFrom(YIELD_NAME);
        //@formatter:on
    }

    @RequestMapping("/foodItem")
    @ResponseBody
    public FoodItem getFoodItem(int foodId) {
        return FoodItem.of(foodNamesPerFoodId.get(foodId), nutrientAmountsPerFoodId.get(foodId));
    }

    @RequestMapping("/foodItems")
    @ResponseBody
    public List<FoodIdAndDescription> getFoodItems() {
        return foodNames
            .stream()
            .map(foodName -> FoodIdAndDescription.of(foodName.getFoodId(), foodName.getFoodDescription()))
            .collect(Collectors.toList());
    }

    private static <K, E> Map<K, E> getMap(List<E> elements, Function<E, K> keyMapper) {
        return elements
            .stream()
            .collect(toMap(keyMapper, Function.identity()));
    }

    private void printMockDataForUi(
        Map<Integer, FoodName> foodNamesPerFoodId,
        Map<Integer, NutrientName> nutrientNamesPerNutrientId,
        Map<Integer, List<NutrientAmount>> nutrientAmountsPerFoodId) {


        List<Integer> nutrientsDisplayedUi = Arrays.asList(208, 205, 269, 291, 203, 204, 605, 606, 601, 301, 303, 307, 319, 321, 418, 401, 324, 868, 869);
        List<Integer> foodIds = Arrays.asList(2, 3582, 502230, 4883, 4919, 502555, 4616, 502440, 502718);
        System.out.print("foodItem,");
        for (int nutrientId : nutrientsDisplayedUi) {
            System.out.print(nutrientNamesPerNutrientId.get(nutrientId).getNutrientName().replace(",", "-") + ",");
        }
        System.out.println();
        for (int foodId : foodIds) {
            if (foodNamesPerFoodId.keySet().contains(foodId)) {
                Map<Integer, Double> nutrientInFood = nutrientAmountsPerFoodId.get(foodId)
                    .stream()
                    .collect(toMap(NutrientAmount::getNutrientId, NutrientAmount::getNutrientValue));
                System.out.print(foodNamesPerFoodId.get(foodId).getFoodDescription().replace(",", "-") + ",");
                for (int nutrientId : nutrientsDisplayedUi) {
                    double nutrientValue = nutrientInFood.keySet().contains(nutrientId)
                        ? nutrientInFood.get(nutrientId)
                        : 0;
                    System.out.print(nutrientValue + ",");
                }
                System.out.println();
            }
        }
    }

}
