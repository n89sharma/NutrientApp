package nutrientapp.user;

import nutrientapp.domain.repositories.UserWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserWeightRepository userWeightRepository;

    @Autowired
    public UserService(UserWeightRepository userWeightRepository) {
        this.userWeightRepository = userWeightRepository;
    }

    public List<BodyWeight> saveWeightAtTime(BodyWeight weightAtTime) {
        userWeightRepository.save(weightAtTime);
        return userWeightRepository.findByUserId(weightAtTime.getUserId());
    }
}