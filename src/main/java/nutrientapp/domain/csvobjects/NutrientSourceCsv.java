package nutrientapp.domain.csvobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
@Document(collection = "nutrient_sources")
public class NutrientSourceCsv {
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
