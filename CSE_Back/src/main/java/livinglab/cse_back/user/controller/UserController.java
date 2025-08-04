package livinglab.cse_back.user.controller;

import livinglab.cse_back.user.dto.LoginDTO;
import livinglab.cse_back.user.entity.User;
import livinglab.cse_back.user.service.UserService;
import livinglab.cse_back.order.dto.TodaySalesDTO;
import livinglab.cse_back.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderRepository orderRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        userService.signup(request.getName(), request.getEmail(), request.getPassword(), request.getRole());
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/today-sales")
    public ResponseEntity<TodaySalesDTO> getTodaySales() {
        LocalDate today = LocalDate.now();
        long orderCount = orderRepository.countTodayOrders(today);
        int totalRevenue = Optional.ofNullable(orderRepository.sumTodayRevenue(today)).orElse(0);
        String topMenu = orderRepository.findTopMenuToday(today);

        TodaySalesDTO salesResponse = TodaySalesDTO.builder()
                .orderCount(orderCount)
                .totalRevenue(totalRevenue)
                .topMenu(topMenu)
                .build();

        return ResponseEntity.ok(salesResponse);
    }

    // Request DTOs
    public static class SignupRequest {
        private String name;
        private String email;
        private String password;
        private User.Role role;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public User.Role getRole() { return role; }
        public void setRole(User.Role role) { this.role = role; }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
