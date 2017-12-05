package dbobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
public class FoodSource {
    private int foodSourceId;
    private int foodSourceCode;
    private String foodSourceDescription;
    private String foodSourceDescriptionF;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
            .builder()
            .addColumn("foodSourceId", NUMBER)
            .addColumn("foodSourceCode", NUMBER)
            .addColumn("foodSourceDescription")
            .addColumn("foodSourceDescriptionF")
            .build();
    }
}
