package nutrientapp.domain.databaseobjects;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "yield_amounts")
public class DbYieldAmount {
    private String foodId;
    private String yieldId;
    private int yieldAmount;
    private Date yieldDateOfEntry;
}
