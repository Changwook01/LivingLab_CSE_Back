package livinglab.cse_back.subscription.entity;

import jakarta.persistence.*;
import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "food_truck_id"}))
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_truck_id", nullable = false)
    private FoodTruck foodTruck;

    private LocalDateTime subscribedAt = LocalDateTime.now();
    private LocalDateTime unsubscribedAt;
    private boolean isActive = true;

    public void unsubscribe() {
        this.unsubscribedAt = LocalDateTime.now();
        this.isActive = false;
    }
    public Subscription(User user, FoodTruck foodTruck) {
        this.user = user;
        this.foodTruck = foodTruck;
        this.subscribedAt = LocalDateTime.now();
        this.isActive = true;
    }
}
