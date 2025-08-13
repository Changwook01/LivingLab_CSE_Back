package livinglab.cse_back.subscription.controller;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.food_truck.repository.FoodTruckRepository;
import livinglab.cse_back.subscription.dto.SubscriptionDto;
import livinglab.cse_back.subscription.entity.Subscription;
import livinglab.cse_back.subscription.service.SubscriptionService;
import livinglab.cse_back.user.entity.User;
import livinglab.cse_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;
    private final FoodTruckRepository foodTruckRepository;

    // 구독 요청
    @PostMapping("/{userId}/{foodTruckId}")
    public Subscription subscribe(@PathVariable Long userId, @PathVariable Long foodTruckId) {
        User user = userRepository.findById(userId).orElseThrow();
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow();
        return subscriptionService.subscribe(user, foodTruck);
    }

    // 구독 취소
    @DeleteMapping("/{userId}/{foodTruckId}")
    public void unsubscribe(@PathVariable Long userId, @PathVariable Long foodTruckId) {
        User user = userRepository.findById(userId).orElseThrow();
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow();
        subscriptionService.unsubscribe(user, foodTruck);
    }

    @GetMapping("/check/{userId}/{foodTruckId}")
    public boolean isSubscribed(@PathVariable Long userId, @PathVariable Long foodTruckId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<FoodTruck> foodTruckOpt = foodTruckRepository.findById(foodTruckId);

        // 유저 또는 푸드트럭이 존재하지 않으면 false 반환
        if (userOpt.isEmpty() || foodTruckOpt.isEmpty()) {
            return false;
        }

        return subscriptionService.isSubscribed(userOpt.get(), foodTruckOpt.get());
    }

    @GetMapping("/user/{userId}")
    public List<SubscriptionDto> getSubscribedFoodTrucks(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return subscriptionService.getSubscribedFoodTrucks(user).stream()
                .map(SubscriptionDto::new)
                .toList();
    }

    // 푸드트럭의 구독자 리스트
    @GetMapping("/foodtruck/{foodTruckId}")
    public List<Subscription> getSubscribers(@PathVariable Long foodTruckId) {
        FoodTruck foodTruck = foodTruckRepository.findById(foodTruckId).orElseThrow();
        return subscriptionService.getSubscribers(foodTruck);
    }
}
