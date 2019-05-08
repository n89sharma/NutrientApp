package nutrientapp.domain.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Data
public class DailySummaryView {

    private String userId;
    private Date date;

    private List<Portion> breakfast = new ArrayList<>();
    private List<Portion> lunch = new ArrayList<>();
    private List<Portion> dinner = new ArrayList<>();
    private List<Portion> other = new ArrayList<>();

    private DailyTotals dailyTotals;

    @JsonIgnore
    public List<Portion> getPortionsInTheDay() {
        return Stream.of(this.breakfast, this.lunch, this.dinner, this.other)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    @Data
    @AllArgsConstructor
    public static class Portion {
        private Food food;
        private Measure measure;
        private double serving;
    }
}