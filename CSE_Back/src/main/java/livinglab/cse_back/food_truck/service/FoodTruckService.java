package livinglab.cse_back.food_truck.service;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.food_truck.repository.FoodTruckRepository;
import livinglab.cse_back.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
}
