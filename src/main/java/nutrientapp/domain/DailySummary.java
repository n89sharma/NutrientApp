package nutrientapp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class DailySummary {
    @Id
    private String id;
    private String userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date date;
    private double caloriePercentFromProtein;
    private double caloriePercentFromCarbohydrates;
    private double caloriePercentFromFat;
    private double protein;
    private double carbohydrates;
    private double fat;
    private double calories;
}