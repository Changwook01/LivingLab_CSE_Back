package livinglab.cse_back.subscription.service;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.subscription.entity.Subscription;
import livinglab.cse_back.subscription.repository.SubscriptionRepository;
import livinglab.cse_back.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    /**
     * 현재 유저가 특정 푸드트럭을 구독 중인지 확인
     */
    public boolean isSubscribed(User user, FoodTruck foodTruck) {
        return subscriptionRepository.findByUserAndFoodTruck(user, foodTruck)
                .map(Subscription::isActive)
                .orElse(false);
    }

    /**
     * 유저가 푸드트럭을 구독
     */
    public Subscription subscribe(User user, FoodTruck foodTruck) {
        return subscriptionRepository.findByUserAndFoodTruck(user, foodTruck)
                .map(sub -> {
                    if (!sub.isActive()) {
                        sub.setActive(true);
                        sub.setSubscribedAt(java.time.LocalDateTime.now());
                        sub.setUnsubscribedAt(null);
                    }
                    return subscriptionRepository.save(sub);
                })
                .orElseGet(() -> subscriptionRepository.save(new Subscription(user, foodTruck)));
    }

    /**
     * 유저가 푸드트럭 구독 취소
     */
    public void unsubscribe(User user, FoodTruck foodTruck) {
        subscriptionRepository.findByUserAndFoodTruck(user, foodTruck)
                .ifPresent(Subscription::unsubscribe);
    }

    /**
     * 유저가 구독 중인 푸드트럭 목록
     */
    public List<Subscription> getSubscribedFoodTrucks(User user) {
        return subscriptionRepository.findByUserAndIsActiveTrue(user);
    }

    /**
     * 특정 푸드트럭의 구독자 목록
     */
    public List<Subscription> getSubscribers(FoodTruck foodTruck) {
        return subscriptionRepository.findByFoodTruckAndIsActiveTrue(foodTruck);
    }
}
