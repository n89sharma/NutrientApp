package nutrientapp.dbobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;

import java.util.Date;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
public class NutrientAmount {
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
