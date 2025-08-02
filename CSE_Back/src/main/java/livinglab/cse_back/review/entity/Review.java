package livinglab.cse_back.review.entity;

import jakarta.persistence.*;
import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ User와 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // ✅ FoodTruck와 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_truck_id")
    private FoodTruck foodTruck;

    @Column(nullable = false)
    private Integer rating; // 1~5 제한은 @Valid에서 처리 가능

    private String comment;

    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
