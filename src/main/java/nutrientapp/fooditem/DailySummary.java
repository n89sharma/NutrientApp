package nutrientapp.fooditem;

import org.springframework.data.annotation.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

@Data
public class DailySummary {
    @Id
    private String id;
    private String userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date date;
    private double caloryPercentFromProtein;
    private double caloryPercentFromCarbohydrates;
    private double caloryPercentFromFat;
    private double protein;
    private double carbohydrates;
    private double fat;
    private double calories;
}