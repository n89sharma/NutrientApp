package nutrientapp.fooditem;

import nutrientapp.dbobjects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static nutrientapp.dbobjects.Tables.*;

@Component
class DataInput {

    private static List<FoodCsv> foods = getAllTableRowsFrom(FOOD_NAME);
    private static Map<Integer, FoodCsv> foodNamesPerFoodId = getMap(foods, FoodCsv::getFoodId);
    
    private static List<NutrientAmountCsv> nutrientAmounts = getAllTableRowsFrom(NUTRIENT_AMOUNT);
    private static Map<Integer, List<NutrientAmountCsv>> nutrientAmountsPerFoodId = nutrientAmounts.stream()
        .collect(Collectors.groupingBy(NutrientAmountCsv::getFoodId, toList()));

    private static List<ConversionFactorCsv> conversionFactors = getAllTableRowsFrom(CONVERSION_FACTOR);
    private static Map<Integer, List<ConversionFactorCsv>> conversionFactorsPerFoodId = conversionFactors.stream()
        .collect(Collectors.groupingBy(ConversionFactorCsv::getFoodId, toList()));

    private static List<MeasureCsv> measureCsvs = getAllTableRowsFrom(MEASURE_NAME);
    private static Map<Integer, MeasureCsv> measurePerMeasureId = getMap(measureCsvs, MeasureCsv::getMeasureId);

    private FoodItemRepository foodItemRepository;
    private FoodIdAndDescriptionRepository foodIdAndDescriptionRepository;
    private MeasuresRepository measuresRepository;
    private FoodGroupRepository foodGroupRepository;

    @Autowired
    public DataInput(
        FoodItemRepository foodItemRepository,
        FoodIdAndDescriptionRepository foodIdAndDescriptionRepository,
        MeasuresRepository measuresRepository,
        FoodGroupRepository foodGroupRepository) {

        this.foodItemRepository = foodItemRepository;
        this.foodIdAndDescriptionRepository = foodIdAndDescriptionRepository;
        this.measuresRepository = measuresRepository;
        this.foodGroupRepository = foodGroupRepository;
    }

    //Saving data to repository
    public void saveItemsToRepository() {

        List<FoodItem> foodItems = foodNamesPerFoodId.values().stream()
                .map(food -> foodItemOf(food, nutrientAmountsPerFoodId.get(food.getFoodId())))
                .collect(toList());
        foodItemRepository.save(foodItems);

        // List<FoodIdAndDescription> foodIdAndDescriptions = foodNamesPerFoodId.values().stream()
        //         .map(food -> FoodIdAndDescription.of(food.getFoodId(), food.getFoodDescription()))
        //         .collect(toList());
        // foodIdAndDescriptionRepository.save(foodIdAndDescriptions);

        // List<Integer> incorrectMeasureIds = new ArrayList<>();
        // List<Measures> measuresForFoodItems = new ArrayList<>();
        // for (int foodId : conversionFactorsPerFoodId.keySet()) {
        //     Measures measures = new Measures();
        //     for (ConversionFactorCsv conversionFactor : conversionFactorsPerFoodId.get(foodId)) {

        //         MeasureCsv measureCsv = measurePerMeasureId.get(conversionFactor.getMeasureId());
        //         if(null != measureCsv) {
        //             String measureName = measureCsv.getMeasureDescription();
        //             Double conversionValue = conversionFactor.getConversionFactorValue();
        //             measures.add(foodId, measureName, conversionValue);
        //         }
        //         else {
        //             incorrectMeasureIds.add(conversionFactor.getMeasureId());
        //         }

        //     }
        //     measuresForFoodItems.add(measures);
        // }
        // List<Integer> uniqueMeasureIds = incorrectMeasureIds.stream().distinct().collect(toList());
        // System.out.println(uniqueMeasureIds);
        // measuresRepository.save(measuresForFoodItems);
    }

    private static <K, E> Map<K, E> getMap(List<E> elements, Function<E, K> keyMapper) {
        return elements.stream().collect(toMap(keyMapper, Function.identity()));
    }

    private static void testMeasureAmount() {
        Scanner reader = new Scanner(System.in);
        while (true) {
            int foodId = reader.nextInt();
            System.out.println(foodNamesPerFoodId.get(foodId).getFoodDescription());
            for (ConversionFactorCsv conversionFactor : conversionFactorsPerFoodId.get(foodId)) {
                int measureIdForFood = conversionFactor.getMeasureId();
                System.out.println(measurePerMeasureId.get(measureIdForFood).getMeasureDescription() + " : "
                        + conversionFactor.getConversionFactorValue());
            }
        }
    }

    private static void printMockDataForUi(Map<Integer, FoodCsv> foodNamesPerFoodId,
            Map<Integer, NutrientNameCsv> nutrientNamesPerNutrientId,
            Map<Integer, List<NutrientAmountCsv>> nutrientAmountsPerFoodId) {

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
                        .collect(toMap(NutrientAmountCsv::getNutrientId, NutrientAmountCsv::getNutrientValue));
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

    public static FoodItem foodItemOf(FoodCsv foodCsv, List<NutrientAmountCsv> nutrientAmounts) {
        FoodItem foodItem = new FoodItem();
        foodItem.setFoodId(foodCsv.getFoodId());
        foodItem.setFoodDescription(foodCsv.getFoodDescription());

        //@formatter:off
        foodItem.setCalories(       getNutrientAmount(208, nutrientAmounts));
        foodItem.setProtein(        getNutrientAmount(203, nutrientAmounts));
        foodItem.setCarbohydrates(  getNutrientAmount(205, nutrientAmounts));
        foodItem.setSugars(         getNutrientAmount(269, nutrientAmounts));
        foodItem.setFibre(          getNutrientAmount(291, nutrientAmounts));
        foodItem.setFats(           getNutrientAmount(204, nutrientAmounts));
        foodItem.setSaturatedFats(  getNutrientAmount(606, nutrientAmounts));
        foodItem.setTransFats(      getNutrientAmount(605, nutrientAmounts));
        foodItem.setCholesterol(    getNutrientAmount(601, nutrientAmounts));
        foodItem.setSodium(         getNutrientAmount(307, nutrientAmounts));
        foodItem.setIron(           getNutrientAmount(303, nutrientAmounts));
        foodItem.setCalcium(        getNutrientAmount(301, nutrientAmounts));

        double retinolAmount =      getNutrientAmount(319, nutrientAmounts);
        double betaCaroteneAMount = getNutrientAmount(321, nutrientAmounts);

        foodItem.setVitaminA(       retinolAmount*3.33 + betaCaroteneAMount*1.66);
        foodItem.setVitaminB12(     getNutrientAmount(418, nutrientAmounts));
        foodItem.setVitaminC(       getNutrientAmount(401, nutrientAmounts));
        foodItem.setVitaminD(       getNutrientAmount(324, nutrientAmounts));
        foodItem.setOmega3(         getNutrientAmount(868, nutrientAmounts));
        foodItem.setOmega6(         getNutrientAmount(869, nutrientAmounts));
        //@formatter:on


       foodItem.setFoodGroupId(foodCsv.getFoodGroupId());

        return foodItem;
    }

    private static double getNutrientAmount(int nutrientId, List<NutrientAmountCsv> nutrientAmounts) {
        return nutrientAmounts
            .stream()
            .filter(nutrientAmount -> nutrientAmount.getNutrientId() == nutrientId)
            .findFirst()
            .map(NutrientAmountCsv::getNutrientValue)
            .orElse(0.0);
    }
}