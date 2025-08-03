// 파일 위치: livinglab/cse_back/zone_geojson/repository/ZoneGeoRepository.java

package livinglab.cse_back.zone_geojson.repository;

import livinglab.cse_back.zone_geojson.dto.ZoneGeoDto;
import livinglab.cse_back.zone_geojson.entity.ZoneGeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneGeoRepository extends JpaRepository<ZoneGeo, Long> {

    @Query(value = "SELECT " +
            "z.id, z.name, z.address, " +
            "ST_Y(z.geom) AS latitude, " +
            "ST_X(z.geom) AS longitude " +
            "FROM zones_geojson z " + // ◀️ 테이블 이름을 수정했습니다.
            "WHERE ST_DWithin(" +
            "   z.geom::geography, " +
            "   ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)::geography, " +
            "   :radius" +
            ")",
            nativeQuery = true)
    List<ZoneGeoDto> findNearbyZones( // ◀️ 반환 타입을 DTO로 변경했습니다.
                                      @Param("latitude") Double latitude,
                                      @Param("longitude") Double longitude,
                                      @Param("radius") Integer radius
    );

    @Query(value = "SELECT " +
            "z.id, z.name, z.address, " +
            "ST_Y(z.geom) AS latitude, " +
            "ST_X(z.geom) AS longitude " +
            "FROM zones_geojson z " + // ◀️ 테이블 이름을 수정했습니다.
            "WHERE z.geom && ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 4326)",
            nativeQuery = true)
    List<ZoneGeoDto> findZonesInRegion( // ◀️ 반환 타입을 DTO로 변경했습니다.
                                        @Param("minLat") Double minLat,
                                        @Param("maxLat") Double maxLat,
                                        @Param("minLon") Double minLon,
                                        @Param("maxLon") Double maxLon
    );
}