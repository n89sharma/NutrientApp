package nutrientapp.domain.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@AllArgsConstructor
public class Food {
    private int foodId;
    private String foodDescription;
    private double calories;

    private MacroNutrients macroNutrients;
    private MicroNutrients microNutrients;

    @JsonIgnore
    public void multiplyByFactor(double factor) {
        this.calories*=factor;
        this.macroNutrients.multiplyByFactor(factor);
        this.microNutrients.multiplyByFactor(factor);
    }
}