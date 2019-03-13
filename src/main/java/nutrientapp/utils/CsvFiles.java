package nutrientapp.utils;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Getter;
import lombok.Setter;
import nutrientapp.domain.csvobjects.ConversionFactorCsv;
import nutrientapp.domain.csvobjects.FoodCsv;
import nutrientapp.domain.csvobjects.FoodGroupCsv;
import nutrientapp.domain.csvobjects.FoodSourceCsv;
import nutrientapp.domain.csvobjects.MeasureNameCsv;
import nutrientapp.domain.csvobjects.NutrientAmountCsv;
import nutrientapp.domain.csvobjects.NutrientNameCsv;
import nutrientapp.domain.csvobjects.NutrientSourceCsv;
import nutrientapp.domain.csvobjects.RefuseAmountCsv;
import nutrientapp.domain.csvobjects.RefuseNameCsv;
import nutrientapp.domain.csvobjects.YieldAmountCsv;
import nutrientapp.domain.csvobjects.YieldNameCsv;

import java.io.File;
import java.util.Collections;
import java.util.List;

public enum CsvFiles {
    // @formatter:off
    CONVERSION_FACTOR(ConversionFactorCsv.getCsvSchema(), "CONVERSION_FACTOR.csv", ConversionFactorCsv.class),
    FOOD_GROUP(FoodGroupCsv.getCsvSchema(), "FOOD_GROUP.csv", FoodGroupCsv.class),
    FOOD_NAME(FoodCsv.getCsvSchema(), "FOOD_NAME.csv", FoodCsv.class),
    FOOD_SOURCE(FoodSourceCsv.getCsvSchema(), "FOOD_SOURCE.csv", FoodSourceCsv.class),
    MEASURE_NAME(MeasureNameCsv.getCsvSchema(), "MEASURE_NAME.csv", MeasureNameCsv.class),
    NUTRIENT_AMOUNT(NutrientAmountCsv.getCsvSchema(), "NUTRIENT_AMOUNT.csv", NutrientAmountCsv.class),
    NUTRIENT_NAME(NutrientNameCsv.getCsvSchema(), "NUTRIENT_NAME.csv", NutrientNameCsv.class),
    NUTRIENT_SOURCE(NutrientSourceCsv.getCsvSchema(), "NUTRIENT_SOURCE.csv", NutrientSourceCsv.class),
    REFUSE_AMOUNT(RefuseAmountCsv.getCsvSchema(), "REFUSE_AMOUNT.csv", RefuseAmountCsv.class),
    REFUSE_NAME(RefuseNameCsv.getCsvSchema(), "REFUSE_NAME.csv", RefuseNameCsv.class),
    YIELD_AMOUNT(YieldAmountCsv.getCsvSchema(), "YIELD_AMOUNT.csv", YieldAmountCsv.class),
    YIELD_NAME(YieldNameCsv.getCsvSchema(), "YIELD_NAME.csv", YieldNameCsv.class);
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

    CsvFiles(CsvSchema csvSchema, String fileName, Class clazz) {
        this.csvSchema = csvSchema;
        this.fileName = fileName;
        this.clazz = clazz;
    }

    public static <T> List<T> getAllTableRowsFrom(CsvFiles table) {
        try {
            return (List<T>) new CsvMapper()
                    .readerFor(table.getClazz())
                    .with(table.getCsvSchema())
                    .readValues(getFile(table.getFileName()))
                    .readAll();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private static File getFile(String fileName) {
        return new File(FoodGroupCsv.class
                .getClassLoader()
                .getResource(fileName)
                .getPath());
    }
}
