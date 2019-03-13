package nutrientapp.nutrient;

import lombok.Data;

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

}
