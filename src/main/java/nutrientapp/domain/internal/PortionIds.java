package nutrientapp.domain.internal;

import lombok.Data;

@Data
public class PortionIds {
    private int foodId;
    private int measureId;
    private double serving;
}
