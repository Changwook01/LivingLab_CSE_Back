package livinglab.cse_back.zone_geojson.entity;

import jakarta.persistence.*;
import org.locationtech.jts.geom.Point; // ❗ 이 import 문을 반드시 추가해야 합니다.

@Entity
@Table(name = "zones_geojson")
public class ZoneGeo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @Column(name = "max_capacity")
    private int maxCapacity;

    @Column(columnDefinition = "geometry(Point, 4326)") // DB 컬럼 타입 명시
    private Point geom;
}
