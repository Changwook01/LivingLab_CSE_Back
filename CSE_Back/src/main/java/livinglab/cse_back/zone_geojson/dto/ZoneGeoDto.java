package livinglab.cse_back.zone_geojson.dto;

// Lombok을 사용한다면 아래 3개의 어노테이션만 남겨두세요.
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ZoneGeoDto {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
}