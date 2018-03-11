package nutrientapp.dbobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
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
