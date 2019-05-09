package nutrientapp.nutrient;

import lombok.val;
import nutrientapp.domain.databaseobjects.DbNutrientAmount;
import nutrientapp.domain.databaseobjects.DbNutrientName;
import nutrientapp.domain.internal.Nutrient;
import nutrientapp.domain.repositories.NutrientAmountRepository;
import nutrientapp.domain.repositories.NutrientNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class NutrientService {
    private final Map<String, DbNutrientName> nutrientNamesByNameId;
    private final Map<Integer, DbNutrientName> nutrientNamesByNutrientCode;
    private NutrientAmountRepository nutrientAmountRepository;
    private NutrientNameRepository nutrientNameRepository;

    @Autowired
    NutrientService(
            NutrientAmountRepository nutrientAmountRepository,
            NutrientNameRepository nutrientNameRepository) {

        this.nutrientAmountRepository = nutrientAmountRepository;
        this.nutrientNameRepository = nutrientNameRepository;
        this.nutrientNamesByNameId = nutrientNameRepository
                .findAll()
                .stream()
                .collect(toMap(DbNutrientName::getNutrientNameId, identity()));
        this.nutrientNamesByNutrientCode = nutrientNameRepository
                .findAll()
                .stream()
                .collect(toMap(DbNutrientName::getNutrientCode, identity()));
    }

    public List<Nutrient> getNutrients(String foodId) {
        val nutrients = new ArrayList<Nutrient>();
        val dbNutrientAmounts = nutrientAmountRepository.findByFoodId(foodId);
        for (val dbNutrientAmount : dbNutrientAmounts) {
            val dbNutrientName = nutrientNamesByNameId.get(dbNutrientAmount.getNutrientNameId());
            nutrients.add(mapDbToDomain(dbNutrientAmount, dbNutrientName));
        }
        return nutrients;
    }

    private Nutrient mapDbToDomain(DbNutrientAmount dbNutrientAmount, DbNutrientName dbNutrientName) {
        val nutrient = new Nutrient();
        nutrient.setNutrientCode(dbNutrientName.getNutrientCode());
        nutrient.setName(dbNutrientName.getNutrientName());
        nutrient.setFrenchName(dbNutrientName.getNutrientNameF());
        nutrient.setSymbol(dbNutrientName.getNutrientSymbol());
        nutrient.setUnit(dbNutrientName.getNutrientUnit());

        nutrient.setAmountValue(dbNutrientAmount.getNutrientValue());
        nutrient.setStandardError(dbNutrientAmount.getStandardError());
        nutrient.setNumberOfObservation(dbNutrientAmount.getNumberOfObservations());
        return nutrient;
    }

    public Nutrient getEmptyNutrient(int nutrientCode) {
        val dbNutrientName = nutrientNamesByNutrientCode.get(nutrientCode);
        val nutrient = new Nutrient();
        nutrient.setNutrientCode(dbNutrientName.getNutrientCode());
        nutrient.setName(dbNutrientName.getNutrientName());
        nutrient.setFrenchName(dbNutrientName.getNutrientNameF());
        nutrient.setSymbol(dbNutrientName.getNutrientSymbol());
        nutrient.setUnit(dbNutrientName.getNutrientUnit());

        nutrient.setAmountValue(0.0);
        nutrient.setStandardError(null);
        nutrient.setNumberOfObservation(null);
        return nutrient;
    }
}
