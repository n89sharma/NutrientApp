package nutrientapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{userId}/weight", method = POST)
    @ResponseBody
    public List<BodyWeight> addWeightAtTime(@RequestBody BodyWeight weightAtTime) {
        return userService.saveWeightAtTime(weightAtTime);
    }

    @RequestMapping(value = "/{userId}/data/{date}/food-summary", method = POST)
    @ResponseBody
    public String addDailySummary(
            @PathVariable String userId,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") Date date,
            @RequestBody DailySummary dailySummary) {

        return userService.saveDailySummary(dailySummary);
    }

}
