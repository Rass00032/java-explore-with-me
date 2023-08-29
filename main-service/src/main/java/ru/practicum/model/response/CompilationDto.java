package ru.practicum.model.response;

import lombok.experimental.FieldDefaults;
import lombok.*;

import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    Integer id;
    Boolean pinned;
    String title;
    List<EventShortDto> events;
}
