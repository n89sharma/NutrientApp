package nutrientapp.fooditem;

import nutrientapp.dbobjects.ConversionFactor;
import nutrientapp.dbobjects.Food;
import nutrientapp.dbobjects.MeasureCsv;
import nutrientapp.dbobjects.NutrientAmount;
import nutrientapp.dbobjects.NutrientName;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static nutrientapp.dbobjects.Tables.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class DataInput {

    private static List<Food> foods = getAllTableRowsFrom(FOOD_NAME);
    private static Map<Integer, Food> foodNamesPerFoodId = getMap(foods, Food::getFoodId);

    private static List<NutrientAmount> nutrientAmounts = getAllTableRowsFrom(NUTRIENT_AMOUNT);
    private static Map<Integer, List<NutrientAmount>> nutrientAmountsPerFoodId = nutrientAmounts.stream()
        .collect(Collectors.groupingBy(NutrientAmount::getFoodId, toList()));

    private static List<ConversionFactor> conversionFactors = getAllTableRowsFrom(CONVERSION_FACTOR);
    private static Map<Integer, List<ConversionFactor>> conversionFactorsPerFoodId = conversionFactors.stream()
        .collect(Collectors.groupingBy(ConversionFactor::getFoodId, toList()));

    private static List<MeasureCsv> measureCsvs = getAllTableRowsFrom(MEASURE_NAME);
    private static Map<Integer, MeasureCsv> measurePerMeasureId = getMap(measureCsvs, MeasureCsv::getMeasureId);

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
                .map(food -> FoodItem.of(food, nutrientAmountsPerFoodId.get(food.getFoodId())))
                .collect(toList());
        foodItemRepository.save(foodItems);

        List<FoodIdAndDescription> foodIdAndDescriptions = foodNamesPerFoodId.values().stream()
                .map(food -> FoodIdAndDescription.of(food.getFoodId(), food.getFoodDescription()))
                .collect(toList());
        foodIdAndDescriptionRepository.save(foodIdAndDescriptions);

        List<Integer> incorrectMeasureIds = new ArrayList<>();
        List<Measures> measuresForFoodItems = new ArrayList<>();
        for (int foodId : conversionFactorsPerFoodId.keySet()) {
            Measures measures = new Measures();
            for (ConversionFactor conversionFactor : conversionFactorsPerFoodId.get(foodId)) {

                MeasureCsv measureCsv = measurePerMeasureId.get(conversionFactor.getMeasureId());
                if(null != measureCsv) {
                    String measureName = measureCsv.getMeasureDescription();
                    Double conversionValue = conversionFactor.getConversionFactorValue();
                    measures.add(foodId, measureName, conversionValue);
                }
                else {
                    incorrectMeasureIds.add(conversionFactor.getMeasureId());
                }

            }
            measuresForFoodItems.add(measures);
        }
        List<Integer> uniqueMeasureIds = incorrectMeasureIds.stream().distinct().collect(toList());
        System.out.println(uniqueMeasureIds);
        conversionFactorRepository.save(measuresForFoodItems);
    }

    private static <K, E> Map<K, E> getMap(List<E> elements, Function<E, K> keyMapper) {
        return elements.stream().collect(toMap(keyMapper, Function.identity()));
    }

    private static void testMeasureAmount() {
        Scanner reader = new Scanner(System.in);
        while (true) {
            int foodId = reader.nextInt();
            System.out.println(foodNamesPerFoodId.get(foodId).getFoodDescription());
            for (ConversionFactor conversionFactor : conversionFactorsPerFoodId.get(foodId)) {
                int measureIdForFood = conversionFactor.getMeasureId();
                System.out.println(measurePerMeasureId.get(measureIdForFood).getMeasureDescription() + " : "
                        + conversionFactor.getConversionFactorValue());
            }
        }
    }

    private static void printMockDataForUi(Map<Integer, Food> foodNamesPerFoodId,
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