package nutrientapp.domain.databaseobjects;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "refuse_names")
public class RefuseName {
    @Id
    private String refuseId;
    private String refuseDescription;
    private String refuseDescriptionF;
}
