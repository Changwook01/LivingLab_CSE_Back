package livinglab.cse_back.menu.controller;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.food_truck.service.FoodTruckService;
import livinglab.cse_back.menu.entity.Menu;
import livinglab.cse_back.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/foodtruck/{foodTruckId}")
    public List<Menu> getMenus(@PathVariable Long foodTruckId) {
        return menuService.getMenusByFoodTruckId(foodTruckId);
    }

    // ✅ 메뉴 삭제
    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
    }

}
