package nutrientapp.domain.csvobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
@Document(collection = "refuse_amounts")
public class RefuseAmountCsv {
    private int foodId;
    private int refuseId;
    private int refuseAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date refuseDateOfEntry;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
                .builder()
                .addColumn("foodId", NUMBER)
                .addColumn("refuseId", NUMBER)
                .addColumn("refuseAmount", NUMBER)
                .addColumn("refuseDateOfEntry")
                .build();
    }
}
