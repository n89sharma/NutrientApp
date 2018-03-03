package nutrientapp.dbobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;

import java.util.Date;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
public class YieldAmount {
    private int foodId;
    private int yieldId;
    private int yieldAmount;
    private Date yieldDateOfEntry;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
            .builder()
            .addColumn("foodId", NUMBER)
            .addColumn("yieldId", NUMBER)
            .addColumn("yieldAmount", NUMBER)
            .addColumn("yieldDateOfEntry")
            .build();
    }
}
