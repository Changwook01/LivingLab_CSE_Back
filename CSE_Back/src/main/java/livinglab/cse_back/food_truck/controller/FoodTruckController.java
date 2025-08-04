package livinglab.cse_back.food_truck.controller;

import livinglab.cse_back.food_truck.dto.StartBusinessRequest;
import livinglab.cse_back.food_truck.service.FoodTruckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/food-trucks")
@RequiredArgsConstructor
public class FoodTruckController {
    private final FoodTruckService foodTruckService;

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
}
