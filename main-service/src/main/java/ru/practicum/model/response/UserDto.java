package ru.practicum.model.response;

import lombok.experimental.FieldDefaults;
import lombok.*;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    Integer id;
    String email;
    String name;
}
