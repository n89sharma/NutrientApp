package nutrientapp.domain.csvobjects;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
@Document(collection = "nutrient_names")
public class NutrientNameCsv {
    private int nutrientNameId;
    private int nutrientCode;
    private String nutrientSymbol;
    private String nutrientUnit;
    private String nutrientName;
    private String nutrientNameF;
    private String tagName;
    private int nutrientDecimals;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
                .builder()
                .addColumn("nutrientNameId", NUMBER)
                .addColumn("nutrientCode", NUMBER)
                .addColumn("nutrientSymbol")
                .addColumn("nutrientUnit")
                .addColumn("nutrientName")
                .addColumn("nutrientNameF")
                .addColumn("tagName")
                .addColumn("nutrientDecimals", NUMBER)
                .build();
    }
}
