package nutrientapp.user;

import lombok.Data;

@Data
public class DailyTotals {
    private double caloriePercentFromProteins;
    private double caloriePercentFromCarbohydrates;
    private double caloriePercentFromFats;
    private double caloriePercentFromOther;

    private double totalProtein;
    private double totalCarbohydrates;
    private double totalFat;
    private double totalCalories;
}
