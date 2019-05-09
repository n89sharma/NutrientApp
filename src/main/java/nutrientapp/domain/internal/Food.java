package nutrientapp.domain.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import net.minidev.json.annotate.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {
    private String foodId;
    private String foodDescription;
    private double calories;

    private MacroNutrients macroNutrients;
    private MicroNutrients microNutrients;

    @JsonIgnore
    public void multiplyByFactor(double factor) {
        this.calories *= factor;
        this.macroNutrients.multiplyByFactor(factor);
        this.microNutrients.multiplyByFactor(factor);
    }

    @JsonIgnore
    public static Food add(Food a, Food b) {
        a.getMacroNutrients().add(b.getMacroNutrients());
        a.getMicroNutrients().add(b.getMicroNutrients());
        return a;
    }
}