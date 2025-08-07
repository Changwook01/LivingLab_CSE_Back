package livinglab.cse_back.user.dto;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.menu.entity.Menu;
import livinglab.cse_back.order.dto.TodaySalesDTO;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PartnerDTO {
    private FoodTruck foodTruck;
    private List<Menu> menus;
    private TodaySalesDTO todaySales;
}