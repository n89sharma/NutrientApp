package nutrientapp.domain.databaseobjects;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "yield_names")
public class YieldName {
    @Id
    private String yieldId;
    private String yieldDescription;
    private String yieldDescriptionF;
}
