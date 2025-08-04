package livinglab.cse_back.food_truck.entity;

import jakarta.persistence.*;
import livinglab.cse_back.menu.entity.Menu;
import livinglab.cse_back.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import org.locationtech.jts.geom.Point; // ◀️ Point 타입을 임포트합니다.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "food_trucks")
public class FoodTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @Column(name = "license_image_url")
    private String licenseImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status = Status.CLOSED;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Status {
        OPERATING, // 영업 중
        CLOSED     // 영업 종료
    }

    @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus;

    @Column(name = "location", columnDefinition = "geography(Point, 4326)")
    private Point location;
}
