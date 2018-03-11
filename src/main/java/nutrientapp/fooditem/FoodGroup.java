package nutrientapp.fooditem;

import org.springframework.data.annotation.Id;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "foodgroups")
public class FoodGroup {
    
    @Id
    String id;
    private int foodGroupId;
    private int foodGroupCode;
    private String foodGroupNameEnglish;
    private String foodGroupNameFrench;

    public static FoodGroup of(int foodGroupId, String foodGroupNameEnglish) {
        FoodGroup foodGroup = new FoodGroup();
        foodGroup.setFoodGroupId(foodGroupId);
        foodGroup.setFoodGroupNameEnglish(foodGroupNameEnglish);
        return foodGroup;
    }
    public boolean isDairyOrEggProduct() {
        return this.foodGroupId == 1;
    }

    public boolean isMeatProduct() {
        return isPoultry() || isSausageAndLuncheonMeat() || isPorkProduct() || isBeefProduct() || isLambVealOrGame();
    }

    public boolean isPorkProduct() {
        return this.foodGroupId == 10;
    }

    public boolean isBeefProduct() {
        return this.foodGroupId == 13;
    }

    private boolean isLambVealOrGame() {
        return this.foodGroupId == 17;
    }

    private boolean isPoultry() {
        return this.foodGroupId == 5;
    }

    private boolean isSausageAndLuncheonMeat() {
        return this.foodGroupId == 7;
    }

    public boolean isVegetableProduct() {
        return this.foodGroupId == 11;
    }

    public boolean isNutsOrSeeds() {
        return this.foodGroupId == 12;
    }

    public boolean isFinfishOrShellfishProduct() {
        return this.foodGroupId == 15;
    }


}