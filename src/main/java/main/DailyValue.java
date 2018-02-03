package main;

import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import static java.util.Arrays.asList;

public enum DailyValue {

    FAT("Fat", 75.0, asList(1,2));

    private String printName;
    private double dailyValue;
    private List<Integer> nutrientNameIds;

    DailyValue(String printName, double dailyValue, List<Integer> nutrientNameIds) {
        this.printName = printName;
        this.dailyValue = dailyValue;
        this.nutrientNameIds = nutrientNameIds;
    }
}