package livinglab.cse_back.food_truck.service;

import livinglab.cse_back.food_truck.dto.OperatingTruckDto;
import livinglab.cse_back.food_truck.dto.OperatingTruckProjection;
import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.food_truck.repository.FoodTruckRepository;
import livinglab.cse_back.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


import jakarta.persistence.EntityNotFoundException;
import livinglab.cse_back.food_truck.dto.StartBusinessRequest;
import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.food_truck.repository.FoodTruckRepository;
import livinglab.cse_back.food_truck.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodTruckService {

    private final FoodTruckRepository foodTruckRepository;

    // 푸드트럭 등록
    public FoodTruck registerFoodTruck(FoodTruck foodTruck) {
        return foodTruckRepository.save(foodTruck);
    }

    // 푸드트럭 단건 조회
    public FoodTruck getFoodTruck(Long id) {
        return foodTruckRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 푸드트럭이 존재하지 않습니다."));
    }

    // 특정 유저의 푸드트럭 목록 조회
    public List<FoodTruck> getFoodTrucksByOwner(User owner) {
        return foodTruckRepository.findByOwner(owner);
    }

    // 푸드트럭 상태 변경 (승인/반려 등)
    public FoodTruck updateStatus(Long id, FoodTruck.Status status) {
        FoodTruck truck = getFoodTruck(id);
        truck.setStatus(status);
        return foodTruckRepository.save(truck);
    }
    private final GeometryUtil geometryUtil;

    // 영업 시작 로직
    public void startBusiness(Long truckId, StartBusinessRequest request) {
        FoodTruck foodTruck = findFoodTruckById(truckId);

        // 관리자에게 승인된 트럭만 영업을 시작할 수 있도록 예외 처리
        if (foodTruck.getStatus() != FoodTruck.Status.OPERATING && foodTruck.getStatus() != FoodTruck.Status.CLOSED) {
            throw new IllegalStateException("영업을 시작할 수 있는 상태가 아닙니다. (승인 상태: " + foodTruck.getStatus() + ")");
        }

        Point location = geometryUtil.createPoint(request.getLatitude(), request.getLongitude());
        foodTruck.setLocation(location);
        foodTruck.setStatus(FoodTruck.Status.OPERATING);
        // foodTruckRepository.save(foodTruck); // @Transactional 어노테이션으로 자동 저장됩니다.
    }

    // 영업 종료 로직
    public void stopBusiness(Long truckId) {
        FoodTruck foodTruck = findFoodTruckById(truckId);
        foodTruck.setLocation(null);
        foodTruck.setStatus(FoodTruck.Status.CLOSED);
        // foodTruckRepository.save(foodTruck); // @Transactional 어노테이션으로 자동 저장됩니다.
    }

    private FoodTruck findFoodTruckById(Long truckId) {
        return foodTruckRepository.findById(truckId)
                .orElseThrow(() -> new EntityNotFoundException("푸드트럭을 찾을 수 없습니다. ID: " + truckId));
    }
    /**
     * 지도 영역 내 영업 중인 푸드트럭 검색 로직
     */
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션으로 성능 향상
    public List<OperatingTruckDto> findOperatingTrucks(Double minLat, Double maxLat, Double minLon, Double maxLon) {
        List<OperatingTruckProjection> projections = foodTruckRepository.findOperatingTrucksInRegion(minLat, maxLat, minLon, maxLon);

        // Projection 리스트를 DTO 리스트로 변환
        return projections.stream()
                .map(p -> new OperatingTruckDto(p.getId(), p.getName(), p.getLatitude(), p.getLongitude(), p.getMenu()))
                .collect(Collectors.toList());
    }
}
