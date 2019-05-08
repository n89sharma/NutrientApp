package nutrientapp.domain.internal;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "recipe")
public class Recipe {
    private String userId;
    private String name;
    private String description;
    private List<PortionIds> portionIds;
}
