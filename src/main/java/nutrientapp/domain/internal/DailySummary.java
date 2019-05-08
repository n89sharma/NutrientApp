package nutrientapp.domain.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "daily_summary")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailySummary {
    @Id
    private String id;
    private String userId;
    private Date date;

    private List<PortionIds> breakfast = new ArrayList<>();
    private List<PortionIds> lunch = new ArrayList<>();
    private List<PortionIds> dinner = new ArrayList<>();
    private List<PortionIds> other = new ArrayList<>();
}

/**
 * {
 * "userId": "n89sharma"
 * "date": "2019-04-25T21:21:54.268Z"
 * "breakfast": [
 * {
 * "foodId": 124,
 * "measureId": 9000,
 * "serving": 1.0
 * }
 * ]
 * <p>
 * }
 */