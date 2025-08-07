package livinglab.cse_back.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 JSON 응답에서 제외
public class LoginDTO {
    private UserDTO user;
    private PartnerDTO partnerDetails; // 파트너가 아닐 경우 이 필드는 null
}