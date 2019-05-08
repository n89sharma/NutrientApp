package nutrientapp.domain.internal;

import lombok.Data;

@Data
public class PortionIds {
    private String foodId;
    private String measureId;
    private double serving;
}
