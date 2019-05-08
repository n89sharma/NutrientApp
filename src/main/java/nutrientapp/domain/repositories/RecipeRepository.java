package nutrientapp.domain.repositories;

import nutrientapp.domain.internal.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

}
