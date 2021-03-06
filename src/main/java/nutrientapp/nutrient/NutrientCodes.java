package nutrientapp.nutrient;

public enum NutrientCodes {
    CALORIES(208),
    PROTEIN(203),
    CARBOHYDRATES(205),
    SUGARS(269),
    FIBRE(291),
    FAT(204),
    SATURATED_FAT(606),
    TRANS_FAT(605),
    CHOLESTEROL(601),
    SODIUM(307),
    IRON(303),
    CALCIUM(301),
    MAGNESIUM(304),
    POTASSIUM(306),
    ZINC(309),
    RETINOL(319),
    BETA_CAROTENE(321),
    ALPHA_CAROTENE(322),
    BETA_CRYPTOXANTHIN(334),
    VITAMIN_B6(415),
    VITAMIN_B12(418),
    VITAMIN_C(401),
    VITAMIN_D(324),
    OMEGA3(902),
    OMEGA6(903);

    private int nutrientCode;

    NutrientCodes(int nutrientCode) {
        this.nutrientCode = nutrientCode;
    }

    public int getNutrientCode() {
        return nutrientCode;
    }
}
