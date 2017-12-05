package dbobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
public class NutrientSource {
    private int nutrientSourceId;
    private int nutrientSourceCode;
    private String nutrientSourceDescription;
    private String nutrientSourceDescriptionF;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
            .builder()
            .addColumn("nutrientSourceId", NUMBER)
            .addColumn("nutrientSourceCode", NUMBER)
            .addColumn("nutrientSourceDescription")
            .addColumn("nutrientSourceDescriptionF")
            .build();
    }
}
