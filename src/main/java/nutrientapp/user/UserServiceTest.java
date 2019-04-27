package nutrientapp.user;

import nutrientapp.NutrientApp;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NutrientApp.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    public void testDate() {

    }
}
