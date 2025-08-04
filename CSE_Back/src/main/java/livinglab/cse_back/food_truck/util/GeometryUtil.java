package livinglab.cse_back.food_truck.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Component;

@Component

public class GeometryUtil {
    private final GeometryFactory geometryFactory;

    public GeometryUtil() {
        // GPS 표준 좌표계인 WGS84 (SRID: 4326)를 사용하는 GeometryFactory 생성
        this.geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    }

    public Point createPoint(double latitude, double longitude) {
        // JTS Point는 (x, y) 순서이므로 (경도, 위도) 순서로 좌표를 생성해야 합니다.
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }
}
