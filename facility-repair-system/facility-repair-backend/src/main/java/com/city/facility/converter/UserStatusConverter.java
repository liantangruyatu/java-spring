package com.city.facility.converter;

import com.city.facility.entity.User;
import org.springframework.stereotype.Component;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<User.UserStatus, String> {

    @Override
    public String convertToDatabaseColumn(User.UserStatus attribute) {
        return attribute != null ? attribute.name().toLowerCase() : null;
    }

    @Override
    public User.UserStatus convertToEntityAttribute(String dbData) {
        return dbData != null ? User.UserStatus.valueOf(dbData.toUpperCase()) : null;
    }
}
