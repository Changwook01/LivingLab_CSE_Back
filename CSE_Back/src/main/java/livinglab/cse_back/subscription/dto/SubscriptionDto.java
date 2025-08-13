package livinglab.cse_back.subscription.dto;

import livinglab.cse_back.subscription.entity.Subscription;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class SubscriptionDto {
    private Long foodTruckId;
    private String foodTruckName;
    private LocalDateTime subscribedAt;
    private boolean isActive;

    public SubscriptionDto(Subscription subscription) {
        this.foodTruckId = subscription.getFoodTruck().getId();
        this.foodTruckName = subscription.getFoodTruck().getName();
        this.subscribedAt = subscription.getSubscribedAt();
        this.isActive = subscription.isActive();
    }
}
