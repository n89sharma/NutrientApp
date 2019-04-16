package nutrientapp.fooditem;

import lombok.Data;

@Data
public class Measure {
    private String measureName;
    private String measureNameF;
    private double conversionFactor;
}
