package livinglab.cse_back.menu.repository;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByFoodTruckId(Long foodTruckId);
    List<Menu> findAll();
}
