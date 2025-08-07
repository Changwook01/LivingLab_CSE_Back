package livinglab.cse_back.user.dto;

import livinglab.cse_back.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private User.Role role;
    private String truckName; // 파트너의 경우 트럭 이름

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
