package nutrientapp.domain.databaseobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "food_names")
public class Food {
    @Id
    private String foodId;
    private int foodCode;
    private String foodGroupId;
    private String foodSourceId;
    private String foodDescription;
    private String foodDescriptionF;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date foodDateOfEntry;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date foodDateOfPublication;
    private int countryCode;
    private String scientificName;
}
