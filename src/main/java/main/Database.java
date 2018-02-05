package main;

import dbobjects.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static dbobjects.Tables.*;
import static java.util.stream.Collectors.toMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Database {

    public static void main(String[] args) throws IOException {
        //formatter:off
        List<ConversionFactor> conversionFactors = getAllTableRowsFrom(CONVERSION_FACTOR);
        List<FoodGroup> foodGroups = getAllTableRowsFrom(FOOD_GROUP);
        List<FoodName> foodNames = getAllTableRowsFrom(FOOD_NAME);
        List<FoodSource> foodSources = getAllTableRowsFrom(FOOD_SOURCE);
        List<MeasureName> measureNames = getAllTableRowsFrom(MEASURE_NAME);
        List<NutrientAmount> nutrientAmounts = getAllTableRowsFrom(NUTRIENT_AMOUNT);
        List<NutrientName> nutrientNames = getAllTableRowsFrom(NUTRIENT_NAME);
        List<NutrientSource> nutrientSources = getAllTableRowsFrom(NUTRIENT_SOURCE);
        List<RefuseAmount> refuseAmounts = getAllTableRowsFrom(REFUSE_AMOUNT);
        List<RefuseName> refuseNames = getAllTableRowsFrom(REFUSE_NAME);
        List<YieldAmount> yieldAmounts = getAllTableRowsFrom(YIELD_AMOUNT);
        List<YieldName> yieldNames = getAllTableRowsFrom(YIELD_NAME);
        //formatter:on

        // foodNames
        //     .stream()
        //     .filter(f -> f.getFoodId() == 2098)
        //     .forEach(f -> System.out.println(f));
        

        Map<Integer, FoodName> foodNameMap = getMap(foodNames, FoodName::getFoodId);
        Map<Integer, NutrientName> nutrientNamesMap = getMap(nutrientNames, NutrientName::getNutrientNameId);
        Map<Integer, List<NutrientAmount>> nutrientAmountsPerFoodId = nutrientAmounts
            .stream()
            .collect(Collectors.groupingBy(NutrientAmount::getFoodId, Collectors.toList()));

        List<Integer> nutrientsDisplayedUi = Arrays.asList(208, 205, 269, 291, 203, 204, 605, 606, 601, 301, 303, 307, 319, 321, 418, 401, 324, 868, 869);
        List<Integer> foodIds = Arrays.asList(2, 3582, 502230, 4883, 4919, 502555, 4616, 502440, 502718);

        for(int foodId : foodIds) {
            if(foodNameMap.keySet().contains(foodId)) {
                Map<Integer, Double> nutrientInFood = nutrientAmountsPerFoodId.get(foodId)
                    .stream()
                    .collect(toMap(NutrientAmount::getNutrientId, NutrientAmount::getNutrientValue));
                for(int nutrientId : nutrientsDisplayedUi) {
                    double nutrientValue = nutrientInFood.keySet().contains(nutrientId)
                        ? nutrientInFood.get(nutrientId)
                        : 0;
                    System.out.print(nutrientValue + ",");
//                    System.out.println(nutrientId + " " + nutrientName + " " + nutrientValue + " " + nutrientUnit);
                }
                System.out.println();
            }
        }
    }

    private static <K, E> Map<K, E> getMap(List<E> elements, Function<E, K> keyMapper) {
        return elements
            .stream()
            .collect(toMap(keyMapper, Function.identity()));
    }

}
