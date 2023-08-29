package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.entity.User;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.request.NewUserRequest;
import ru.practicum.model.response.UserDto;
import ru.practicum.storage.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        if (ids == null) {
            List<User> users = userRepository.findAll(PageRequest.of(from / size, size)).getContent();
            return userMapper.toUserDtos(users);
        }
        List<User> users = userRepository.findAllByIdIn(ids, PageRequest.of(from / size, size)).getContent();
        return userMapper.toUserDtos(users);
    }

    public UserDto createUser(NewUserRequest newUserRequest) {
        User user = userMapper.toUser(newUserRequest);
        try {
            User savedUser = userRepository.save(user);
            return userMapper.toUserDto(savedUser);
        } catch (DataAccessException e) {
            throw new ConflictException("Пользователь с имением " + user.getName() + " уже существует");
        }
    }

    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Пользователь не найден");
        userRepository.deleteById(userId);
    }
}
