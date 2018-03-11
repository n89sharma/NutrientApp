package nutrientapp.dbobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;

import java.util.Date;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
public class ConversionFactorCsv {
    private int foodId;
    private int measureId;
    private double conversionFactorValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date conversionFactorDateOfEntry;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
            .builder()
            .addColumn("foodId", NUMBER)
            .addColumn("measureId", NUMBER)
            .addColumn("conversionFactorValue", NUMBER)
            .addColumn("conversionFactorDateOfEntry")
            .build();
    }
}
