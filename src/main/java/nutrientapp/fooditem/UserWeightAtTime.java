package nutrientapp.fooditem;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "userweight")
public class UserWeightAtTime {
    @Id
    String id;
    private String userId;
    private double weight;
    private WeightUnit unit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date date; //2018-03-03T20:55:13.535Z
}
/*

import java.util.Date;
import java.time.Instant;
class Main {
  public static void main(String[] args) {
    System.out.println(Date.from(Instant.parse("2018-03-04T20:55:13.535Z")));
  }
}


*/