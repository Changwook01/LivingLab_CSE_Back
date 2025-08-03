package livinglab.cse_back.food_truck.controller;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.food_truck.service.FoodTruckService;
import livinglab.cse_back.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foodtrucks")
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

}
