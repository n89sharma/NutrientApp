package nutrientapp.domain.internal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemSummary {
    private String id;
    private String description;
    private String descriptionF;
    private ItemType itemType;

    public enum ItemType {
        FOOD, RECIPE, USER_FOOD;
    }
}
