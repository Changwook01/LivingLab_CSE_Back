// 파일 위치: livinglab/cse_back/zone_geojson/controller/ZoneGeoController.java

package livinglab.cse_back.zone_geojson.controller;

import livinglab.cse_back.zone_geojson.dto.ZoneGeoDto;
import livinglab.cse_back.zone_geojson.repository.ZoneGeoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneGeoController {

    private final ZoneGeoRepository zoneGeoRepository;
    private static final Logger logger = LoggerFactory.getLogger(ZoneGeoController.class);

    @Autowired
    public ZoneGeoController(ZoneGeoRepository zoneGeoRepository) {
        this.zoneGeoRepository = zoneGeoRepository;
    }

    @GetMapping("/nearby")
    public List<ZoneGeoDto> getNearbyZones(
            @RequestParam("lat") Double latitude,
            @RequestParam("lon") Double longitude,
            @RequestParam(value = "rad", defaultValue = "1000") Integer radius
    ) {
        logger.info("주변 구역 검색 요청: lat={}, lon={}, radius={}", latitude, longitude, radius);
        // ✅ 변환 과정 없이 바로 리포지토리 결과를 반환합니다.
        return zoneGeoRepository.findNearbyZones(latitude, longitude, radius);
    }


    @GetMapping("/region")
    public List<ZoneGeoDto> getZonesInRegion(
            @RequestParam("minLat") Double minLat,
            @RequestParam("maxLat") Double maxLat,
            @RequestParam("minLon") Double minLon,
            @RequestParam("maxLon") Double maxLon
    ) {
        logger.info("지도 영역 내 구역 검색 요청: minLat={}, maxLat={}, minLon={}, maxLon={}", minLat, maxLat, minLon, maxLon);
        // ✅ 변환 과정 없이 바로 리포지토리 결과를 반환합니다.
        return zoneGeoRepository.findZonesInRegion(minLat, maxLat, minLon, maxLon);
    }

}