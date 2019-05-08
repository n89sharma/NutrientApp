package nutrientapp.domain.databaseobjects;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "measure_names")
public class MeasureName {
    @Id
    private String measureId;
    private String measureDescription;
    private String measureDescriptionF;
}
