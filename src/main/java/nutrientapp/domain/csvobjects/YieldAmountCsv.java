package nutrientapp.domain.csvobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
@Document(collection = "yield_amounts")
public class YieldAmountCsv {
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
