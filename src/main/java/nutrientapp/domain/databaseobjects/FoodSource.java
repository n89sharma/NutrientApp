package nutrientapp.domain.databaseobjects;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "food_sources")
public class FoodSource {
    @Id
    private String foodSourceId;
    private int foodSourceCode;
    private String foodSourceDescription;
    private String foodSourceDescriptionF;

}
