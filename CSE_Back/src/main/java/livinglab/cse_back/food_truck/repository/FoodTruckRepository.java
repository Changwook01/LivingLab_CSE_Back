package livinglab.cse_back.food_truck.repository;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {
}
