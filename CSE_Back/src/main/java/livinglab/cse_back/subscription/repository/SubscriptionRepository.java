package livinglab.cse_back.subscription.repository;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.subscription.entity.Subscription;
import livinglab.cse_back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    // 특정 유저와 푸드트럭의 구독 여부 확인
    Optional<Subscription> findByUserAndFoodTruck(User user, FoodTruck foodTruck);

    // 유저가 구독한 모든 푸드트럭 (활성 구독만)
    List<Subscription> findByUserAndIsActiveTrue(User user);

    // 푸드트럭을 구독 중인 사용자 목록
    List<Subscription> findByFoodTruckAndIsActiveTrue(FoodTruck foodTruck);
}
