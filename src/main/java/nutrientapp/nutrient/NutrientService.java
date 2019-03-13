package nutrientapp.nutrient;

import lombok.val;
import nutrientapp.domain.csvobjects.NutrientAmountCsv;
import nutrientapp.domain.csvobjects.NutrientNameCsv;
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
    private NutrientAmountRepository nutrientAmountRepository;
    private NutrientNameRepository nutrientNameRepository;
    private final Map<Integer, NutrientNameCsv> nutrientNames;

    @Autowired
    NutrientService(NutrientAmountRepository nutrientAmountRepository, NutrientNameRepository nutrientNameRepository) {
        this.nutrientAmountRepository = nutrientAmountRepository;
        this.nutrientNameRepository = nutrientNameRepository;
        this.nutrientNames = nutrientNameRepository
                .findAll()
                .stream()
                .collect(toMap(NutrientNameCsv::getNutrientNameId, identity()));
    }

    public List<Nutrient> getNutrients(int foodId) {
        val nutrients = new ArrayList<Nutrient>();
        val dbNutrientAmounts = nutrientAmountRepository.findByFoodId(foodId);
        for (val dbNutrientAmount : dbNutrientAmounts) {
            val dbNutrientName = nutrientNames.get(dbNutrientAmount.getNutrientNameId());
            nutrients.add(mapDbToDomain(dbNutrientAmount, dbNutrientName));
        }
        return nutrients;
    }

    private Nutrient mapDbToDomain(NutrientAmountCsv dbNutrientAmount, NutrientNameCsv dbNutrientName) {
        val nutrient = new Nutrient();
        nutrient.setNutrientNameId(dbNutrientName.getNutrientNameId());
        nutrient.setName(dbNutrientName.getNutrientName());
        nutrient.setFrenchName(dbNutrientName.getNutrientNameF());
        nutrient.setSymbol(dbNutrientName.getNutrientSymbol());
        nutrient.setUnit(dbNutrientName.getNutrientUnit());

        nutrient.setAmountValue(dbNutrientAmount.getNutrientValue());
        nutrient.setStandardError(dbNutrientAmount.getStandardError());
        nutrient.setNumberOfObservation(dbNutrientAmount.getNumberOfObservations());
        return nutrient;
    }

    public Nutrient getEmptyNutrient(int nutrientNameId) {
        val dbNutrientName = nutrientNames.get(nutrientNameId);
        val nutrient = new Nutrient();
        nutrient.setNutrientNameId(dbNutrientName.getNutrientNameId());
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
