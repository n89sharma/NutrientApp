package dbobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
public class MeasureName {
    private int measureId;
    private String measureDescription;
    private String measureDescriptionF;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
            .builder()
            .addColumn("measureId", NUMBER)
            .addColumn("measureDescription")
            .addColumn("measureDescriptionF")
            .build();
    }
}
