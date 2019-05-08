package nutrientapp.domain.databaseobjects;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "food_groups")
public class FoodGroup {
    @Id
    private String foodGroupId;
    private int foodGroupCode;
    private String foodGroupName;
    private String foodGroupNameF;
}
