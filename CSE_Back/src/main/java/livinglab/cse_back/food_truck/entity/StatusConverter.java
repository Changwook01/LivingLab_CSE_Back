package livinglab.cse_back.food_truck.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<FoodTruck.Status, String> {

    @Override
    public String convertToDatabaseColumn(FoodTruck.Status attribute) {
        if (attribute == null) return null;
        return attribute.name().toLowerCase();
    }

    @Override
    public FoodTruck.Status convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return FoodTruck.Status.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException ex) {
            // 알 수 없는 값이면 기본값으로 처리 (로깅 원하면 추가)
            return FoodTruck.Status.PENDING;
        }
    }
}