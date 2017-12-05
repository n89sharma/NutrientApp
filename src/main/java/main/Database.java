package main;

import dbobjects.*;

import java.io.IOException;
import java.util.List;

import static dbobjects.Tables.*;

public class Database {

    public static void main(String[] args) throws IOException {
        //formatter:off
        List<ConversionFactor> conversionFactors    = getAllTableRowsFrom(CONVERSION_FACTOR);
        List<FoodGroup> foodGroups                  = getAllTableRowsFrom(FOOD_GROUP);
        List<FoodName> foodNames                    = getAllTableRowsFrom(FOOD_NAME);
        List<FoodSource> foodSources                = getAllTableRowsFrom(FOOD_SOURCE);
        List<MeasureName> measureNames              = getAllTableRowsFrom(MEASURE_NAME);
        List<NutrientAmount> nutrientAmounts        = getAllTableRowsFrom(NUTRIENT_AMOUNT);
        List<NutrientName> nutrientNames            = getAllTableRowsFrom(NUTRIENT_NAME);
        List<NutrientSource> nutrientSources        = getAllTableRowsFrom(NUTRIENT_SOURCE);
        List<RefuseAmount> refuseAmounts            = getAllTableRowsFrom(REFUSE_AMOUNT);
        List<RefuseName> refuseNames                = getAllTableRowsFrom(REFUSE_NAME);
        List<YieldAmount> yieldAmounts              = getAllTableRowsFrom(YIELD_AMOUNT);
        List<YieldName> yieldNames                  = getAllTableRowsFrom(YIELD_NAME);
        //formatter:on
    }

}
