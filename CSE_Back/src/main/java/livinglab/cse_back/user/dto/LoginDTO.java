package livinglab.cse_back.user.dto;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.menu.entity.Menu;
import livinglab.cse_back.order.dto.TodaySalesDTO;
import livinglab.cse_back.user.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private User user;
    private User.Role role;
    private FoodTruck foodTruck;
    private List<Menu> menus;
    private TodaySalesDTO todaySales;
}
