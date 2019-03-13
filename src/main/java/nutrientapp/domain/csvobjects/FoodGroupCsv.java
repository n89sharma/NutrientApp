package nutrientapp.domain.csvobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
@Document(collection = "food_groups")
public class FoodGroupCsv {
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
