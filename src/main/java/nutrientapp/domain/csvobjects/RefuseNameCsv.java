package nutrientapp.domain.csvobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
@Document(collection = "refuse_names")
public class RefuseNameCsv {
    private int refuseId;
    private String refuseDescription;
    private String refuseDescriptionF;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
            .builder()
            .addColumn("refuseId", NUMBER)
            .addColumn("refuseDescription")
            .addColumn("refuseDescriptionF")
            .build();
    }
}
