package nutrientapp.dbobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
public class FoodGroup {
    private int foodGroupId;
    private int foodGroupCode;
    private String foodGroupName;
    private String foodGroupNameF;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
            .builder()
            .addColumn("foodGroupId", NUMBER)
            .addColumn("foodGroupCode", NUMBER)
            .addColumn("foodGroupName")
            .addColumn("foodGroupNameF")
            .build();
    }
}
