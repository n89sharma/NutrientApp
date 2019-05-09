package nutrientapp.domain.databaseobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "conversion_factors")
public class DbConversionFactor {
    private String foodId;
    private String measureId;
    private double conversionFactorValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date conversionFactorDateOfEntry;
}
