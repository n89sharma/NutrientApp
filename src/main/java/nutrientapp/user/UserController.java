package nutrientapp.user;

import nutrientapp.domain.internal.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

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

    @RequestMapping(value = "/{userId}/data/{date}/food-summary", method = PUT)
    @ResponseBody
    public DailySummary addDailySummary(
            @PathVariable String userId,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") Date date,
            @RequestBody DailySummary dailySummary) {

        return userService.saveDailySummary(dailySummary, date);
    }

    @RequestMapping(value = "/{userId}/data/{date}/food-summary", method = GET)
    @ResponseBody
    public DailySummaryView getDailySummary(
            @PathVariable String userId,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") Date date) {

        return userService.getDailySummary(userId, date);
    }

    @RequestMapping(value = "/{userId}/data/{date}/total", method = GET)
    @ResponseBody
    public DailyTotals getDailyTotal(
            @PathVariable String userId,
            @PathVariable("date") @DateTimeFormat(pattern = "yyyyMMdd") Date date) {

        return userService.getDailyTotals(userId, date);
    }

    @RequestMapping(value = "/{userId}/recipe", method = POST)
    @ResponseBody
    public Recipe saveRecipe(@RequestBody Recipe recipe) {
        return userService.saveRecipe(recipe);
    }
}
