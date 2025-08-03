package livinglab.cse_back.menu.service;

import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.menu.entity.Menu;
import livinglab.cse_back.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    // 메뉴 추가
    public Menu addMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    // 특정 푸드트럭 메뉴 조회
    public List<Menu> getMenusByFoodTruckId(Long foodTruckId) {
        return menuRepository.findByFoodTruckId(foodTruckId);
    }

    // 메뉴 삭제
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

}
