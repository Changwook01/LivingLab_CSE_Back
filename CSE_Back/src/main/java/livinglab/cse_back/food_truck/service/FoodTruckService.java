package livinglab.cse_back.food_truck.service;

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
}
