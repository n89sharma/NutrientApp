package nutrientapp.dbobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;

import java.util.Date;

import static com.fasterxml.jackson.dataformat.csv.CsvSchema.ColumnType.NUMBER;

@Data
public class FoodCsv {
    private int foodId;
    private int foodCode;
    private int foodGroupId;
    private int foodSourceId;
    private String foodDescription;
    private String foodDescriptionF;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date foodDateOfEntry;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date foodDateOfPublication;
    private int countryCode;
    private String scientificName;

    public static CsvSchema getCsvSchema() {
        return CsvSchema
            .builder()
            .addColumn("foodId", NUMBER)
            .addColumn("foodCode", NUMBER)
            .addColumn("foodGroupId", NUMBER)
            .addColumn("foodSourceId", NUMBER)
            .addColumn("foodDescription")
            .addColumn("foodDescriptionF")
            .addColumn("foodDateOfEntry")
            .addColumn("foodDateOfPublication")
            .addColumn("countryCode", NUMBER)
            .addColumn("scientificName")
            .build();
    }
}
