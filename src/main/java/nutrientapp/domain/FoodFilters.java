package nutrientapp.domain;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public enum FoodFilters {
    VEGAN("Vegan", veganFilter()),
    OVO_VEGETARIAN("Ovo vegetarian", asList()),
    LACTO_VEGETARIAN("Lacto vegetarian", asList()),
    LACTO_OVO_VEGETARIAN("Lacto ovo vegetarian", lactoOvoVegetarianFilter()),
    PESCATARIAN("Pescatarian", pescatarianFilter()),
    PORK("No pork", asList(10)),
    BEEF("No beef", asList(13)),
    NUTS_AND_SEEDS("Beef", asList(12)),
    DAIRY_AND_EGG("Dairy and egg", noDairyOrEgg()),
    FINFISH_ANDSHELLFISH("No finfish or shellsfish", noFish());

    private String description;
    private List<Integer> foodGroupIds;

    private FoodFilters(String description, List<Integer> foodGroupIds) {
        this.description = description;
        this.foodGroupIds = foodGroupIds;
    }

    private static List<Integer> veganFilter() {
        List<Integer> veganFilter = new ArrayList<>();
        veganFilter.addAll(noMeat());
        veganFilter.addAll(noFish());
        veganFilter.addAll(noDairyOrEgg());
        return veganFilter;
    }

    private static List<Integer> lactoOvoVegetarianFilter() {
        List<Integer> ovoVegetarianFilter = new ArrayList<>();
        ovoVegetarianFilter.addAll(noMeat());
        ovoVegetarianFilter.addAll(noFish());
        return ovoVegetarianFilter;
    }

    private static List<Integer> pescatarianFilter() {
        return noMeat();
    }

    private static List<Integer> noMeat() {
        //5 poultry
        //7 sausages and luncheon meats
        //10 pork products
        //13 beef products
        //17 lamb veal and game

        return asList(5, 7, 10, 13, 17);
    }

    private static List<Integer> noDairyOrEgg() {
        return asList(1);
    }

    private static List<Integer> noFish() {
        return asList(15);
    }
}