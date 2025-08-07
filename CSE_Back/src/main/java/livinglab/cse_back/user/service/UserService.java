package livinglab.cse_back.user.service;

import livinglab.cse_back.user.dto.LoginDTO;
import livinglab.cse_back.order.dto.TodaySalesDTO;
import livinglab.cse_back.food_truck.entity.FoodTruck;
import livinglab.cse_back.food_truck.repository.FoodTruckRepository;
import livinglab.cse_back.menu.entity.Menu;
import livinglab.cse_back.menu.repository.MenuRepository;
import livinglab.cse_back.order.repository.OrderRepository;
import livinglab.cse_back.user.dto.PartnerDTO;
import livinglab.cse_back.user.dto.UserDTO;
import livinglab.cse_back.user.entity.User;
import livinglab.cse_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FoodTruckRepository foodTruckRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    // 회원가입
    public void signup(String name, String email, String password, User.Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password) // 평문 저장 (실제 프로덕션에서는 암호화 필요)
                .role(role)
                .build();
        userRepository.save(user);
        
        // 기본 사용자 데이터 추가 (첫 번째 사용자만)
        if (userRepository.count() == 1) {
            addDefaultUsers();
        }
    }

    // 기본 사용자 데이터 추가
    private void addDefaultUsers() {
        // 기본 사용자들 추가
        User operator1 = User.builder()
                .name("김철수")
                .email("chulsoo@example.com")
                .password("password1")
                .role(User.Role.OPERATOR)
                .build();
        userRepository.save(operator1);

        User operator2 = User.builder()
                .name("이영희")
                .email("younghee@example.com")
                .password("password2")
                .role(User.Role.OPERATOR)
                .build();
        userRepository.save(operator2);

        User citizen1 = User.builder()
                .name("박민수")
                .email("minsu@example.com")
                .password("password3")
                .role(User.Role.CITIZEN)
                .build();
        userRepository.save(citizen1);

        User citizen2 = User.builder()
                .name("정다혜")
                .email("dahye@example.com")
                .password("password4")
                .role(User.Role.CITIZEN)
                .build();
        userRepository.save(citizen2);

        User admin = User.builder()
                .name("관리자")
                .email("admin@example.com")
                .password("adminpass")
                .role(User.Role.ADMIN)
                .build();
        userRepository.save(admin);

        // 푸드트럭 데이터 추가
        FoodTruck foodTruck1 = FoodTruck.builder()
                .name("김철수의 맛있는 떡볶이")
                .description("매콤달콤한 떡볶이 전문점")
                .owner(operator1)
                .status(FoodTruck.Status.APPROVED)
                .build();
        foodTruckRepository.save(foodTruck1);

        FoodTruck foodTruck2 = FoodTruck.builder()
                .name("이영희의 건강한 샌드위치")
                .description("신선한 재료로 만드는 샌드위치")
                .owner(operator2)
                .status(FoodTruck.Status.APPROVED)
                .build();
        foodTruckRepository.save(foodTruck2);

        // 메뉴 데이터 추가
        Menu menu1 = Menu.builder()
                .name("떡볶이")
                .price(5000)
                .category("분식")
                .foodTruck(foodTruck1)
                .build();
        menuRepository.save(menu1);

        Menu menu2 = Menu.builder()
                .name("어묵")
                .price(3000)
                .category("분식")
                .foodTruck(foodTruck1)
                .build();
        menuRepository.save(menu2);

        Menu menu3 = Menu.builder()
                .name("치킨 샌드위치")
                .price(8000)
                .category("샌드위치")
                .foodTruck(foodTruck2)
                .build();
        menuRepository.save(menu3);

        Menu menu4 = Menu.builder()
                .name("에그 샌드위치")
                .price(6000)
                .category("샌드위치")
                .foodTruck(foodTruck2)
                .build();
        menuRepository.save(menu4);
    }

    // 로그인 후 응답
    public LoginDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일이 존재하지 않습니다."));

        if (!password.equals(user.getPassword())) { // 실제로는 암호화된 비밀번호 비교 필요
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        UserDTO userDTO = UserDTO.from(user);
        PartnerDTO partnerDetails = null;

        // 사용자가 파트너(OPERATOR)인 경우에만 추가 정보 조회
        if (user.getRole() == User.Role.OPERATOR) {
            FoodTruck foodTruck = foodTruckRepository.findByOwnerId(user.getId()).orElse(null);
            List<Menu> menus = List.of();
            if (foodTruck != null) {
                menus = menuRepository.findByFoodTruckId(foodTruck.getId());
                userDTO.setTruckName(foodTruck.getName()); // UserDTO에 트럭 이름 설정
            }

            // 매출 정보 조회
            LocalDate today = LocalDate.now();
            long orderCount = orderRepository.countTodayOrders(today);
            int totalRevenue = Optional.ofNullable(orderRepository.sumTodayRevenue(today)).orElse(0);
            String topMenu = orderRepository.findTopMenuToday(today);
            TodaySalesDTO salesResponse = TodaySalesDTO.builder()
                    .orderCount(orderCount)
                    .totalRevenue(totalRevenue)
                    .topMenu(topMenu)
                    .build();

            partnerDetails = PartnerDTO.builder()
                    .foodTruck(foodTruck)
                    .menus(menus)
                    .todaySales(salesResponse)
                    .build();
        }

        return LoginDTO.builder()
                .user(userDTO)
                .partnerDetails(partnerDetails) // 파트너가 아니면 이 값은 null
                .build();
    }
}
