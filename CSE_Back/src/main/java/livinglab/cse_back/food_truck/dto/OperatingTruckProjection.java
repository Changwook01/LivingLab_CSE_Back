package livinglab.cse_back.food_truck.dto;

public interface OperatingTruckProjection {
    Long getId();
    String getName();
    Double getLatitude();
    Double getLongitude();
    String getMenu();
}
