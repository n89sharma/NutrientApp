package dbobjects;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.Collections;
import java.util.List;

public enum Tables {
    // @formatter:off
    CONVERSION_FACTOR   (ConversionFactor.getCsvSchema(),   "CONVERSION_FACTOR.csv",    ConversionFactor.class),
    FOOD_GROUP          (FoodGroup.getCsvSchema(),          "FOOD_GROUP.csv",           FoodGroup.class),
    FOOD_NAME           (FoodName.getCsvSchema(),           "FOOD_NAME.csv",            FoodName.class),
    FOOD_SOURCE         (FoodSource.getCsvSchema(),         "FOOD_SOURCE.csv",          FoodSource.class),
    MEASURE_NAME        (MeasureName.getCsvSchema(),        "MEASURE_NAME.csv",         MeasureName.class),
    NUTRIENT_AMOUNT     (NutrientAmount.getCsvSchema(),     "NUTRIENT_AMOUNT.csv",      NutrientAmount.class),
    NUTRIENT_NAME       (NutrientName.getCsvSchema(),       "NUTRIENT_NAME.csv",        NutrientName.class),
    NUTRIENT_SOURCE     (NutrientSource.getCsvSchema(),     "NUTRIENT_SOURCE.csv",      NutrientSource.class),
    REFUSE_AMOUNT       (RefuseAmount.getCsvSchema(),       "REFUSE_AMOUNT.csv",        RefuseAmount.class),
    REFUSE_NAME         (RefuseName.getCsvSchema(),         "REFUSE_NAME.csv",          RefuseName.class),
    YIELD_AMOUNT        (YieldAmount.getCsvSchema(),        "YIELD_AMOUNT.csv",         YieldAmount.class),
    YIELD_NAME          (YieldName.getCsvSchema(),          "YIELD_NAME.csv",           YieldName.class);
    // @formatter:on

    @Getter
    @Setter
    private CsvSchema csvSchema;

    @Getter
    @Setter
    private String fileName;

    @Getter
    @Setter
    private Class clazz;

    Tables(CsvSchema csvSchema, String fileName, Class clazz) {
        this.csvSchema = csvSchema;
        this.fileName = fileName;
        this.clazz = clazz;
    }

    public static <T> List<T> getAllTableRowsFrom(Tables table) {
        try {
            return (List<T>) new CsvMapper()
                .readerFor(table.getClazz())
                .with(table.getCsvSchema())
                .readValues(getFile(table.getFileName()))
                .readAll();
        }
        catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private static File getFile(String fileName) {
        return new File(FoodGroup.class
            .getClassLoader()
            .getResource(fileName)
            .getPath());
    }
}
