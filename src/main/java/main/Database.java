package main;

import dbobjects.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static dbobjects.Tables.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.HashMap;
import java.util.stream.Collectors;

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

        while(true) {
            int foodId = Integer.parseInt(System.console().readLine());
            if(foodNameMap.keySet().contains(foodId)) {
                String foodDescription = foodNameMap.get(foodId).getFoodDescription(); 
                List<NutrientAmount> nutrientsInGivenFood = nutrientAmountsPerFoodId.get(foodId);
                System.out.println(foodDescription + " " + nutrientsInGivenFood.size());        
                for(NutrientAmount nutrientAmount : nutrientsInGivenFood) {
                    int nutrientId              = nutrientAmount.getNutrientId();
                    NutrientName nutrientInfo   = nutrientNamesMap.get(nutrientId);
                    String nutrientName         = nutrientInfo.getNutrientName();
                    double nutrientValue        = nutrientAmount.getNutrientValue();
                    String nutrientUnit         = new String(nutrientInfo.getNutrientUnit().getBytes(), "UTF-8");
                    System.out.println(nutrientName + " " + nutrientValue + " " + nutrientUnit);
                }
            }
        }
    }

    private static <K, E> Map<K, E> getMap(List<E> elements, Function<E, K> keyMapper) {
        return elements
            .stream()
            .collect(Collectors.toMap(keyMapper, Function.identity()));
    }

}
