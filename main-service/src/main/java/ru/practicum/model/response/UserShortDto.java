package ru.practicum.model.response;

import lombok.experimental.FieldDefaults;
import lombok.*;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {
    Long id;
    String name;
}
