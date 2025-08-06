package livinglab.cse_back.food_truck.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FoodTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 엔티티 내부용 관계는 직렬화에서 제외
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

    // 메뉴도 직렬화에서 제외(필요하면 DTO로 변환해서 반환)
    @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Menu> menus;

    // DB에 저장되는 Point (PostGIS)
    @Column(name = "location", columnDefinition = "geometry(Point,4326)")
    @JsonIgnore // JTS Point 객체 자체는 직렬화하지 않음
    private Point location;

    // JSON으로는 latitude/longitude만 노출하도록 transient 필드 추가
    @Transient
    @JsonProperty("location")
    private LocationDto locationDto;

    // 유틸 DTO (내부클래스 또는 별도 파일로 분리 가능)
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationDto {
        private Double latitude;   // getY()
        private Double longitude;  // getX()
    }

    // 엔티티 로드 후 Point -> LocationDto 변환
    @PostLoad
    @PostPersist
    @PostUpdate
    private void populateLocationDto() {
        if (this.location != null) {
            this.locationDto = new LocationDto(this.location.getY(), this.location.getX());
        } else {
            this.locationDto = null;
        }
    }

    // DB 저장 전(선택) latitude/longitude -> Point를 만들고 싶으면 구현 가능
    // (JTS Point 생성에 GeometryFactory 필요 — 필요하면 알려줘)

    public enum Status {
        PENDING, APPROVED, REJECTED, CLOSED, OPERATING
    }
}
