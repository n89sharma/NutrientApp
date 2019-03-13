package nutrientapp.domain.csvobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
@Document(collection = "food_sources")
public class FoodSourceCsv {
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
