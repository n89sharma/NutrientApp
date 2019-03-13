package nutrientapp.domain.csvobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
@Document(collection = "measure_names")
public class MeasureNameCsv {
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
