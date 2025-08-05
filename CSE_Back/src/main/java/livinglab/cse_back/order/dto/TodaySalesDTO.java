package livinglab.cse_back.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodaySalesDTO {
    private long orderCount;
    private int totalRevenue;
    private String topMenu;
}
