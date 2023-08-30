package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.entity.User;
import ru.practicum.model.request.NewUserRequest;
import ru.practicum.model.response.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(NewUserRequest dto);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);
}
