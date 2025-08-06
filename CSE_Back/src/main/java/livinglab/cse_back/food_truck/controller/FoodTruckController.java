package livinglab.cse_back.food_truck.controller;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.food_truck.service.FoodTruckService;
import livinglab.cse_back.user.entity.User;
import livinglab.cse_back.food_truck.dto.OperatingTruckDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import livinglab.cse_back.food_truck.dto.StartBusinessRequest;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/food-trucks")
@RequiredArgsConstructor
public class FoodTruckController {

    private final FoodTruckService foodTruckService;

    // ✅ 푸드트럭 등록
    @PostMapping
    public FoodTruck register(@RequestBody FoodTruck foodTruck) {
        return foodTruckService.registerFoodTruck(foodTruck);
    }

    // ✅ 푸드트럭 단건 조회
    @GetMapping("/{id}")
    public FoodTruck getFoodTruck(@PathVariable Long id) {
        return foodTruckService.getFoodTruck(id);
    }

    // ✅ 특정 유저의 푸드트럭 목록 조회
    @GetMapping("/owner/{ownerId}")
    public List<FoodTruck> getFoodTrucksByOwner(@PathVariable Long ownerId) {
        User owner = new User();
        owner.setId(ownerId);
        return foodTruckService.getFoodTrucksByOwner(owner);
    }

    // ✅ 푸드트럭 상태 변경 (승인/거절)
    @PatchMapping("/{id}/status")
    public FoodTruck updateStatus(@PathVariable Long id, @RequestParam FoodTruck.Status status) {
        return foodTruckService.updateStatus(id, status);
    }


    // 영업 시작 API
    @PostMapping("/{truckId}/start")
    public ResponseEntity<String> startBusiness(
            @PathVariable Long truckId,
            @RequestBody StartBusinessRequest request) {
        foodTruckService.startBusiness(truckId, request);
        return ResponseEntity.ok("정상적으로 영업을 시작했습니다.");
    }

    // 영업 종료 API
    @PostMapping("/{truckId}/stop")
    public ResponseEntity<String> stopBusiness(@PathVariable Long truckId) {
        foodTruckService.stopBusiness(truckId);
        return ResponseEntity.ok("영업을 종료했습니다.");
    }

    /**
     * 지도 영역 내 영업 중인 푸드트럭 검색 API
     */
    @GetMapping("/operating")
    public ResponseEntity<List<OperatingTruckDto>> findOperatingTrucks(
            @RequestParam("minLat") Double minLat,
            @RequestParam("maxLat") Double maxLat,
            @RequestParam("minLon") Double minLon,
            @RequestParam("maxLon") Double maxLon
    ) {
        List<OperatingTruckDto> trucks = foodTruckService.findOperatingTrucks(minLat, maxLat, minLon, maxLon);

        System.out.println(trucks);

        return ResponseEntity.ok(trucks);
    }
}
