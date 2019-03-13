package nutrientapp.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import nutrientapp.fooditem.Food;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "usermeals")
public class UserMealAtTime {
    @Id
    private String id;
    private String userId;
    private Food meal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date date;
}