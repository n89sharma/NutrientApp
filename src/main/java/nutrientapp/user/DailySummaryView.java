package nutrientapp.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import nutrientapp.fooditem.Food;
import nutrientapp.fooditem.Measure;

import java.util.Date;
import java.util.List;

@Data
public class DailySummaryView {

    private String userId;
    private Date date;

    private List<Portion> breakfast;
    private List<Portion> lunch;
    private List<Portion> dinner;
    private List<Portion> other;

    private DailyTotals dailyTotals;

    @Data
    @AllArgsConstructor
    public static class Portion {
        private Food food;
        private Measure measure;
        private double serving;
    }

    @Data
    public static class DailyTotals {
        private double caloriePercentFromProtein;
        private double caloriePercentFromCarbohydrates;
        private double caloriePercentFromFat;
        private double totalProtein;
        private double totalCarbohydrates;
        private double totalFat;
        private double totalCalories;
    }
}