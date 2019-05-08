package nutrientapp.domain.databaseobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "nutrient_amounts")
public class NutrientAmount {
    private String foodId;
    private String nutrientNameId;
    private String nutrientSourceId;
    private double nutrientValue;
    private double standardError;
    private int numberOfObservations;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date nutrientDateOfEntry;
}
