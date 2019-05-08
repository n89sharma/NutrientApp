package nutrientapp.domain.databaseobjects;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "nutrient_sources")
public class NutrientSource {
    @Id
    private String nutrientSourceId;
    private int nutrientSourceCode;
    private String nutrientSourceDescription;
    private String nutrientSourceDescriptionF;
}
