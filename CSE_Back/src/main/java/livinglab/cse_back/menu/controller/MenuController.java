package livinglab.cse_back.menu.controller;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.food_truck.service.FoodTruckService;
import livinglab.cse_back.menu.entity.Menu;
import livinglab.cse_back.menu.service.MenuService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final FoodTruckService foodTruckService;

    // ✅ 메뉴 추가
    @PostMapping
    public Menu addMenu(@RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    // ✅ 특정 푸드트럭 메뉴 조회
    // 추천 방식 1 - 통합 처리
    @GetMapping
public List<Menu> getMenus(@RequestParam(required = false) Long foodTruckId) {
    if (foodTruckId != null) {
        return menuService.getMenusByFoodTruckId(foodTruckId);
    } else {
        return menuService.getAllMenus(); // 전체 메뉴 반환
    }
}

@PutMapping("/{id}")
public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
    Menu updated = menuService.updateMenu(id, menu);
    return ResponseEntity.ok(updated);
}
    
    // ✅ 메뉴 삭제
    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }

    @PutMapping("/menus/{id}/toggle")
    public ResponseEntity<Menu> toggleAvailability(@PathVariable Long id) {
    Menu updated = menuService.toggleAvailability(id);
    return ResponseEntity.ok(updated);
}


}
