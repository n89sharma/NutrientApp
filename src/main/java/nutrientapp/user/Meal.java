package nutrientapp.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import nutrientapp.fooditem.Food;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "user_meals")
public class Meal {
    @Id
    private String id;
    private String userId;
    private List<Food> meal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date date;
}