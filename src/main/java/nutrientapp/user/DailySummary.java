package nutrientapp.user;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "daily_summary")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailySummary {
    @Id
    private String id;
    private String userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    private Date date;

    private List<PortionIds> breakfast;
    private List<PortionIds> lunch;
    private List<PortionIds> dinner;
    private List<PortionIds> other;

    @Data
    public static class PortionIds {
        private int foodId;
        private int measureId;
        private double serving;
    }
}

/**
 * {
 *    "userId": "n89sharma"
 *    "date": "2019-04-25T21:21:54.268Z"
 *    "breakfast": [
 *      {
 *        "foodId": 124,
 *        "measureId": 9000,
 *        "serving": 1.0
 *      }
 *    ]
 *
 * }
 */