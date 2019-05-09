package nutrientapp.domain.databaseobjects;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "nutrient_names")
public class DbNutrientName {
    @Id
    private String nutrientNameId;
    private int nutrientCode;
    private String nutrientSymbol;
    private String nutrientUnit;
    private String nutrientName;
    private String nutrientNameF;
    private String tagName;
    private int nutrientDecimals;
}
