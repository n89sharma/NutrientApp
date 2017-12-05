package main;

import dbobjects.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static dbobjects.Tables.*;

import com.fasterxml.jackson.databind.ObjectMapper;

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

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("./ConversionFactors.json"), conversionFactors);
        mapper.writeValue(new File("./FoodGroups.json"), foodGroups);
        mapper.writeValue(new File("./foodNames.json"), foodNames);
        mapper.writeValue(new File("./FoodSources.json"), foodSources);
        mapper.writeValue(new File("./MeasureNames.json"), measureNames);
        mapper.writeValue(new File("./NutrientAmounts.json"), nutrientAmounts);
        mapper.writeValue(new File("./NutrientNames.json"), nutrientNames);
        mapper.writeValue(new File("./NutrientSources.json"), nutrientSources);
        mapper.writeValue(new File("./RefuseAmounts.json"), refuseAmounts);
        mapper.writeValue(new File("./RefuseNames.json"), refuseNames);
        mapper.writeValue(new File("./YieldAmounts.json"), yieldAmounts);
        mapper.writeValue(new File("./YieldNames.json"), yieldNames);
    }

}
