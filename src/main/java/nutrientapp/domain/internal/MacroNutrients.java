package nutrientapp.domain.internal;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
public class MacroNutrients {
    private Nutrient protein;
    private Nutrient carbohydrates;
    private Nutrient sugars;
    private Nutrient fibre;
    private Nutrient fats;
    private Nutrient saturatedFats;
    private Nutrient transFats;
    private Nutrient cholesterol;

    @JsonIgnore
    public void multiplyByFactor(double factor) {
        this.protein.multiplyByFactor(factor);
        this.carbohydrates.multiplyByFactor(factor);
        this.sugars.multiplyByFactor(factor);
        this.fibre.multiplyByFactor(factor);
        this.fats.multiplyByFactor(factor);
        this.saturatedFats.multiplyByFactor(factor);
        this.transFats.multiplyByFactor(factor);
        this.cholesterol.multiplyByFactor(factor);
    }

    @JsonIgnore
    public void add(MacroNutrients other) {
        this.protein.add(other.getProtein());
        this.carbohydrates.add(other.getCarbohydrates());
        this.sugars.add(other.getSugars());
        this.fibre.add(other.getFibre());
        this.fats.add(other.getFats());
        this.saturatedFats.add(other.getSaturatedFats());
        this.transFats.add(other.getTransFats());
        this.cholesterol.add(other.getCholesterol());
    }
}
