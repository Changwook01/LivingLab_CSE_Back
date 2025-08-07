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

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }
    
    public List<Menu> getAllMenus(Long foodTruckId) {
        if (foodTruckId == null) {
            return getAllMenus(); // 내부에서 호출
        } else {
            return menuRepository.findByFoodTruckId(foodTruckId);
        }
    }

    public List<Menu> getMenusByFoodTruckId(Long foodTruckId) {
        return menuRepository.findByFoodTruckId(foodTruckId);
    }

    // 메뉴 삭제
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    public Menu toggleAvailability(Long id) {
        Menu menu = menuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.setAvailable(!menu.isAvailable());
        return menuRepository.save(menu);
    }

    

    public Menu updateMenu(Long id, Menu updatedMenu) {
        Menu menu = menuRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Menu not found"));
    
        menu.setName(updatedMenu.getName());
        menu.setPrice(updatedMenu.getPrice());
        menu.setCategory(updatedMenu.getCategory());
        menu.setImageUrl(updatedMenu.getImageUrl());
        menu.setAvailable(updatedMenu.isAvailable());
    
        return menuRepository.save(menu);
    }
}
