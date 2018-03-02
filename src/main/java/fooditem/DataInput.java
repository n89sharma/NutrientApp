package fooditem;

import dbobjects.ConversionFactor;
import dbobjects.FoodGroup;
import dbobjects.FoodName;
import dbobjects.FoodSource;
import dbobjects.MeasureName;
import dbobjects.NutrientAmount;
import dbobjects.NutrientName;
import dbobjects.NutrientSource;
import dbobjects.RefuseAmount;
import dbobjects.RefuseName;
import dbobjects.YieldAmount;
import dbobjects.YieldName;

import static dbobjects.Tables.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
class DataInput {

    private static List<NutrientSource> nutrientSources = getAllTableRowsFrom(NUTRIENT_SOURCE);
    private static List<FoodGroup> foodGroups = getAllTableRowsFrom(FOOD_GROUP);
    private static List<FoodSource> foodSources = getAllTableRowsFrom(FOOD_SOURCE);
    private static List<RefuseAmount> refuseAmounts = getAllTableRowsFrom(REFUSE_AMOUNT);
    private static List<RefuseName> refuseNames = getAllTableRowsFrom(REFUSE_NAME);
    private static List<YieldAmount> yieldAmounts = getAllTableRowsFrom(YIELD_AMOUNT);
    private static List<YieldName> yieldNames = getAllTableRowsFrom(YIELD_NAME);

    private static List<FoodName> foodNames = getAllTableRowsFrom(FOOD_NAME);
    private static List<NutrientAmount> nutrientAmounts = getAllTableRowsFrom(NUTRIENT_AMOUNT);
    private static List<NutrientName> nutrientNames = getAllTableRowsFrom(NUTRIENT_NAME);
    private static List<ConversionFactor> conversionFactors = getAllTableRowsFrom(CONVERSION_FACTOR);
    private static List<MeasureName> measureNames = getAllTableRowsFrom(MEASURE_NAME);

    private static Map<Integer, FoodName> foodNamesPerFoodId = getMap(foodNames, FoodName::getFoodId);
    private static Map<Integer, NutrientName> nutrientNamesPerNutrientId = getMap(nutrientNames,
            NutrientName::getNutrientNameId);
    private static Map<Integer, List<NutrientAmount>> nutrientAmountsPerFoodId = nutrientAmounts.stream()
            .collect(Collectors.groupingBy(NutrientAmount::getFoodId, Collectors.toList()));
    private static Map<Integer, List<ConversionFactor>> conversionFactorsPerFoodId = conversionFactors.stream()
            .collect(Collectors.groupingBy(ConversionFactor::getFoodId, Collectors.toList()));
    private static Map<Integer, MeasureName> measureNamesPerMeasureId = getMap(measureNames, MeasureName::getMeasureId);

    private FoodItemRepository foodItemRepository;
    private FoodIdAndDescriptionRepository foodIdAndDescriptionRepository;
    private ConversionFactorRepository conversionFactorRepository;

    @Autowired
    public DataInput(
        FoodItemRepository foodItemRepository,
        FoodIdAndDescriptionRepository foodIdAndDescriptionRepository,
        ConversionFactorRepository conversionFactorRepository) {

        this.foodItemRepository = foodItemRepository;
        this.foodIdAndDescriptionRepository = foodIdAndDescriptionRepository;
        this.conversionFactorRepository = conversionFactorRepository;
    }

    //Saving data to repository
    public void saveItemsToRepository() {

        List<FoodItem> foodItems = foodNamesPerFoodId.values().stream()
                .map(foodName -> FoodItem.of(foodName, nutrientAmountsPerFoodId.get(foodName.getFoodId())))
                .collect(Collectors.toList());
        foodItemRepository.save(foodItems);

        List<FoodIdAndDescription> foodIdAndDescriptions = foodNamesPerFoodId.values().stream()
                .map(foodName -> FoodIdAndDescription.of(foodName.getFoodId(), foodName.getFoodDescription()))
                .collect(Collectors.toList());
        foodIdAndDescriptionRepository.save(foodIdAndDescriptions);

        List<ConversionFactorFoodItem> conversionFactorsForFoodItems = new ArrayList();
        for (int foodId : conversionFactorsPerFoodId.keySet()) {
            List<ConversionFactor> conversionFactors = conversionFactorsPerFoodId.get(foodId);
            Map<String, Double> measures = conversionFactors.stream()
                    .collect(Collectors.toMap(conversionFactor -> measureNamesPerMeasureId
                            .get(conversionFactor.getMeasureId()).getMeasureDescription(),
                            ConversionFactor::getConversionFactorValue));
            conversionFactorsForFoodItems.add(ConversionFactorFoodItem.of(foodId, measures));
        }
        conversionFactorRepository.save(conversionFactorsForFoodItems);

        System.out.println("it is done");
    }

    private static <K, E> Map<K, E> getMap(List<E> elements, Function<E, K> keyMapper) {
        return elements.stream().collect(Collectors.toMap(keyMapper, Function.identity()));
    }

    private static void testMeasureAmount() {
        Scanner reader = new Scanner(System.in);
        while (true) {
            int foodId = reader.nextInt();
            System.out.println(foodNamesPerFoodId.get(foodId).getFoodDescription());
            for (ConversionFactor conversionFactor : conversionFactorsPerFoodId.get(foodId)) {
                int measureIdForFood = conversionFactor.getMeasureId();
                System.out.println(measureNamesPerMeasureId.get(measureIdForFood).getMeasureDescription() + " : "
                        + conversionFactor.getConversionFactorValue());
            }
        }
    }

    private static void printMockDataForUi(Map<Integer, FoodName> foodNamesPerFoodId,
            Map<Integer, NutrientName> nutrientNamesPerNutrientId,
            Map<Integer, List<NutrientAmount>> nutrientAmountsPerFoodId) {

        List<Integer> nutrientsDisplayedUi = Arrays.asList(208, 205, 269, 291, 203, 204, 605, 606, 601, 301, 303, 307,
                319, 321, 418, 401, 324, 868, 869);
        List<Integer> foodIds = Arrays.asList(2, 3582, 502230, 4883, 4919, 502555, 4616, 502440, 502718);
        System.out.print("foodItem,");
        for (int nutrientId : nutrientsDisplayedUi) {
            System.out.print(nutrientNamesPerNutrientId.get(nutrientId).getNutrientName().replace(",", "-") + ",");
        }
        System.out.println();
        for (int foodId : foodIds) {
            if (foodNamesPerFoodId.keySet().contains(foodId)) {
                Map<Integer, Double> nutrientValuePerNutrientIdInFood = nutrientAmountsPerFoodId.get(foodId).stream()
                        .collect(Collectors.toMap(NutrientAmount::getNutrientId, NutrientAmount::getNutrientValue));
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