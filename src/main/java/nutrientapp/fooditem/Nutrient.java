package nutrientapp.fooditem;

import lombok.Data;

@Data
public class Nutrient {
    private double amountValue;
    private String name;
    private String frenchName;
    private String symbol;
    private String unit;
    private double standardError;
    private double numberOfObservation;
}
