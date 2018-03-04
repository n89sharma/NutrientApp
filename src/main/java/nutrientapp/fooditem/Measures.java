package nutrientapp.fooditem;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "measures")
public class Measures {

    @Id
    String id;
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