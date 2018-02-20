package main;

import dbobjects.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import static dbobjects.Tables.*;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Controller
@EnableAutoConfiguration
@CrossOrigin(origins = "http://localhost:3000")
public class NutrientApp {

    //@formatter:off
    private static List<FoodName> foodNames                    = getAllTableRowsFrom(FOOD_NAME);
    private static List<NutrientAmount> nutrientAmounts        = getAllTableRowsFrom(NUTRIENT_AMOUNT);
    private static List<NutrientName> nutrientNames            = getAllTableRowsFrom(NUTRIENT_NAME);
    private static List<ConversionFactor> conversionFactors    = getAllTableRowsFrom(CONVERSION_FACTOR);
    private static List<MeasureName> measureNames              = getAllTableRowsFrom(MEASURE_NAME);

    private static Map<Integer, FoodName> foodNamesPerFoodId                   = getMap(foodNames, FoodName::getFoodId);
    private static Map<Integer, NutrientName> nutrientNamesPerNutrientId       = getMap(nutrientNames, NutrientName::getNutrientNameId);
    private static Map<Integer, List<NutrientAmount>> nutrientAmountsPerFoodId = nutrientAmounts
        .stream()
        .collect(groupingBy(NutrientAmount::getFoodId, toList()));
    private static Map<Integer, List<ConversionFactor>> conversionFactorsPerFoodId   = conversionFactors
        .stream()
        .collect(groupingBy(ConversionFactor::getFoodId, toList()));
    private static Map<Integer, MeasureName> measureNamesPerMeasureId           = getMap(measureNames, MeasureName::getMeasureId);
    //@formatter:on

    public static void main(String[] args) throws IOException {

        SpringApplication.run(NutrientApp.class, args);

        //@formatter:off
        List<NutrientSource> nutrientSources        = getAllTableRowsFrom(NUTRIENT_SOURCE);
        List<FoodGroup> foodGroups                  = getAllTableRowsFrom(FOOD_GROUP);
        List<FoodSource> foodSources                = getAllTableRowsFrom(FOOD_SOURCE);

        List<RefuseAmount> refuseAmounts            = getAllTableRowsFrom(REFUSE_AMOUNT);
        List<RefuseName> refuseNames                = getAllTableRowsFrom(REFUSE_NAME);
        List<YieldAmount> yieldAmounts              = getAllTableRowsFrom(YIELD_AMOUNT);
        List<YieldName> yieldNames                  = getAllTableRowsFrom(YIELD_NAME);
        //@formatter:on

        testMeasureAmount();
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
            .collect(toList());
    }

    @RequestMapping("/conversionOptions")
    @ResponseBody
    public Map<String, Double> getConversionOptions(int foodId) {
        List<ConversionFactor> conversionFactors = conversionFactorsPerFoodId.get(foodId);

        return conversionFactors
            .stream()
            .collect(toMap(
                f-> measureNamesPerMeasureId.get(f.getMeasureId()).getMeasureDescription(),
                ConversionFactor::getConversionFactorValue));
    }

    private static <K, E> Map<K, E> getMap(List<E> elements, Function<E, K> keyMapper) {
        return elements
            .stream()
            .collect(toMap(keyMapper, identity()));
    }

    private static void testMeasureAmount() {
        Scanner reader = new Scanner(System.in);
        while (true) {
            int foodId = reader.nextInt();
            System.out.println(foodNamesPerFoodId.get(foodId).getFoodDescription());
            for(ConversionFactor conversionFactor: conversionFactorsPerFoodId.get(foodId)) {
                int measureIdForFood = conversionFactor.getMeasureId();
                System.out.println(
                    measureNamesPerMeasureId.get(measureIdForFood).getMeasureDescription() +
                    " : " +
                    conversionFactor.getConversionFactorValue());
            }
        }
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
                Map<Integer, Double> nutrientValuePerNutrientIdInFood = nutrientAmountsPerFoodId.get(foodId)
                    .stream()
                    .collect(toMap(NutrientAmount::getNutrientId, NutrientAmount::getNutrientValue));
                System.out.print(foodNamesPerFoodId.get(foodId).getFoodDescription().replace(",", "-") + ",");
                for (int nutrientId : nutrientsDisplayedUi) {
                    double nutrientValue = nutrientValuePerNutrientIdInFood.keySet().contains(nutrientId)
                        ? nutrientValuePerNutrientIdInFood.get(nutrientId)
                        : 0;
                    System.out.print(nutrientValue + ",");
                }
                System.out.println();
            }
        }
    }

}
