package nutrientapp.domain.internal;

import lombok.Data;

@Data
public class Measure {
    private String measureId;
    private String measureName;
    private String measureNameF;
    private double conversionFactor;
}
