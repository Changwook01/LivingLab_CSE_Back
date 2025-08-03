package livinglab.cse_back.user.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<User.Role, String> {

    @Override
    public String convertToDatabaseColumn(User.Role attribute) {
        if (attribute == null) return null;
        return attribute.name().toLowerCase(); // DB에 소문자로 저장
    }

    @Override
    public User.Role convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return User.Role.valueOf(dbData.toUpperCase()); // DB 소문자 -> Enum 대문자 변환
    }
}

