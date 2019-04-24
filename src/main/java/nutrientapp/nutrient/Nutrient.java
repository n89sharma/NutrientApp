package nutrientapp.nutrient;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
public class Nutrient {
    //name
    private int nutrientNameId;
    private String name;
    private String frenchName;
    private String symbol;
    private String unit;

    //amount
    private Double amountValue;
    private Double standardError;
    private Integer numberOfObservation;

    @JsonIgnore
    public void multiplyByFactor(double factor) {
        this.amountValue*=factor;
    }

}
