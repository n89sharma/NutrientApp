package nutrientapp.domain.internal;

import lombok.Data;

public class DailyTotals {

    private MacroDistribution macroDistribution = new MacroDistribution();
    private MealDistribution mealDistribution = new MealDistribution();

    public MacroDistribution getMacroDistribution() {
        return macroDistribution;
    }

    public MealDistribution getMealDistribution() {
        return mealDistribution;
    }

    public void setMacroDistribution(
            double proteins,
            double carbohydrates,
            double fats,
            double other,
            double totalProteins,
            double totalCarbohydrates,
            double totalFats,
            double totalCalories) {

        this.macroDistribution.setProteins(proteins);
        this.macroDistribution.setCarbohydrates(carbohydrates);
        this.macroDistribution.setFats(fats);
        this.macroDistribution.setOther(other);

        this.macroDistribution.setTotalProteins(totalProteins);
        this.macroDistribution.setTotalCarbohydrates(totalCarbohydrates);
        this.macroDistribution.setTotalFats(totalFats);
        this.macroDistribution.setTotalCalories(totalCalories);
    }

    public void setMealDistribution(
            double breakfast,
            double lunch,
            double dinner,
            double other,
            double totalBreakfast,
            double totalLunch,
            double totalDinner,
            double totalOther) {

        this.mealDistribution.setBreakfast(breakfast);
        this.mealDistribution.setLunch(lunch);
        this.mealDistribution.setDinner(dinner);
        this.mealDistribution.setOther(other);

        this.mealDistribution.setTotalBreakfast(totalBreakfast);
        this.mealDistribution.setTotalLunch(totalLunch);
        this.mealDistribution.setTotalDinner(totalDinner);
        this.mealDistribution.setTotalOther(totalOther);
    }

    @Data
    public static class MacroDistribution {
        private double proteins;
        private double carbohydrates;
        private double fats;
        private double other;

        private double totalProteins;
        private double totalCarbohydrates;
        private double totalFats;
        private double totalCalories;
    }

    @Data
    public static class MealDistribution {
        private double breakfast;
        private double lunch;
        private double dinner;
        private double other;

        private double totalBreakfast;
        private double totalLunch;
        private double totalDinner;
        private double totalOther;
    }
}
