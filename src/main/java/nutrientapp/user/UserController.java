package nutrientapp.user;

import nutrientapp.domain.internal.BodyWeight;
import nutrientapp.domain.internal.DailySummary;
import nutrientapp.domain.internal.DailySummaryView;
import nutrientapp.domain.internal.DailyTotals;
import nutrientapp.domain.internal.ItemSummary;
import nutrientapp.fooditem.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private UserService userService;
    private FoodItemService foodItemService;

    @Autowired
    public UserController(UserService userService, FoodItemService foodItemService) {
        this.userService = userService;
        this.foodItemService = foodItemService;
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

    @RequestMapping(value = "/{userId}/food", method = GET)
    public List<ItemSummary> getItemSummaries(@PathVariable String userId) {
        return userService.getAllItemSummaries(userId);
    }
}
