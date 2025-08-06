package livinglab.cse_back.food_truck.repository;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.user.entity.User;
import livinglab.cse_back.food_truck.dto.OperatingTruckProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {
    List<FoodTruck> findByOwner(User owner);
    Optional<FoodTruck> findByOwnerId(Long ownerId);

    /**
     * 지도 영역 내에서 '영업 중'인 푸드트럭과 대표 메뉴를 검색하는 쿼리
     */
    @Query(value = "SELECT " +
            "    ft.id, " +
            "    ft.name, " +
            "    ST_Y(ft.location) AS latitude, " +
            "    ST_X(ft.location) AS longitude, " +
            "    (SELECT m.name FROM menus m WHERE m.food_truck_id = ft.id LIMIT 1) AS menu " +
            "FROM " +
            "    food_trucks ft " +
            "WHERE " +
            "    ft.status = 'operating' AND " +
            "    ft.location IS NOT NULL AND " +
            "    ft.location && ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 4326)",
            nativeQuery = true)
    List<OperatingTruckProjection> findOperatingTrucksInRegion(
            @Param("minLat") Double minLat,
            @Param("maxLat") Double maxLat,
            @Param("minLon") Double minLon,
            @Param("maxLon") Double maxLon
    );
}
