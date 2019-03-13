package nutrientapp.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import nutrientapp.domain.WeightUnit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "user_weights")
public class UserWeightAtTime {
    @Id
    String id;
    private String userId;
    private double weight;
    private WeightUnit unit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date date;
}