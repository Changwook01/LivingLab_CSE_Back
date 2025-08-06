package livinglab.cse_back.food_truck.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OperatingTruckDto {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String menu;
}
