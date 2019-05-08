package nutrientapp.domain.databaseobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "refuse_amounts")
public class RefuseAmount {
    private String foodId;
    private String refuseId;
    private int refuseAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date refuseDateOfEntry;
}
