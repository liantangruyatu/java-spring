package com.city.facility.converter;

import com.city.facility.entity.User;
import org.springframework.stereotype.Component;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<User.UserType, String> {

    @Override
    public String convertToDatabaseColumn(User.UserType attribute) {
        return attribute != null ? attribute.name().toLowerCase() : null;
    }

    @Override
    public User.UserType convertToEntityAttribute(String dbData) {
        return dbData != null ? User.UserType.valueOf(dbData.toUpperCase()) : null;
    }
}
