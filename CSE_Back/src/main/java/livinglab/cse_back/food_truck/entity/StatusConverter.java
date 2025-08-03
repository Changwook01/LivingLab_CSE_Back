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
        return FoodTruck.Status.valueOf(dbData.toUpperCase());
    }
}
