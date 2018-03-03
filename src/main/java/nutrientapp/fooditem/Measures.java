package nutrientapp.fooditem;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "measures")
public class Measures {

    int foodId;
    List<Measure> measures = new ArrayList<>();

    public void add(int foodId, String measureDescription, double measureConversionFactor) {
        this.foodId = foodId;
        measures.add(new Measure(measureDescription, measureConversionFactor));
    }

    @Data
    @AllArgsConstructor
    private class Measure {
        private String measureDescription;
        private double measureConversionFactor;
    }
}