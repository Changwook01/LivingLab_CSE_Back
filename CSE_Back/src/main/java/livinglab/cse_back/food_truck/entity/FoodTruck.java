package livinglab.cse_back.food_truck.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import livinglab.cse_back.menu.entity.Menu;
import livinglab.cse_back.user.entity.User;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.List;

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
    @JsonIgnore
    private User owner;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @Column(name = "license_image_url")
    private String licenseImageUrl;

    @Convert(converter = StatusConverter.class)
    @Column(length = 20)
    private Status status = Status.PENDING;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /** ✅ PostGIS Point 타입으로 위치 저장 */
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Menu> menus;

    /** ✅ CLOSED 상태 추가 */
    public enum Status {
        PENDING, APPROVED, REJECTED, CLOSED
    }
}
