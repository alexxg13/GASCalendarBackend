package com.example.gascalendar.mappers;

import com.example.gascalendar.dto.response.UserResponse;
import com.example.gascalendar.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse userToUserResponse(User user);
}
