package dbobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
public class YieldName {
    private int yieldId;
    private String yieldDescription;
    private String yieldDescriptionF;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
            .builder()
            .addColumn("yieldId", NUMBER)
            .addColumn("yieldDescription")
            .addColumn("yieldDescriptionF")
            .build();
    }
}
