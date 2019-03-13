package nutrientapp.domain.csvobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
@Document(collection = "nutrient_amounts")
public class NutrientAmountCsv {
    private int foodId;
    private int nutrientId;
    private double nutrientValue;
    private double standardError;
    private int numberOfObservations;
    private int nutrientSourceId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date nutrientDateOfEntry;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
            .builder()
            .addColumn("foodId", NUMBER)
            .addColumn("nutrientId", NUMBER)
            .addColumn("nutrientValue", NUMBER)
            .addColumn("standardError", NUMBER)
            .addColumn("numberOfObservations", NUMBER)
            .addColumn("nutrientSourceId", NUMBER)
            .addColumn("nutrientDateOfEntry")
            .build();
    }
}
