package livinglab.cse_back.food_truck.repository;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {
    List<FoodTruck> findByOwner(User owner);
}
