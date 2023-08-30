package ru.practicum.model.response;

import lombok.experimental.FieldDefaults;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    Integer id;
    @NotBlank
    String text;
    LocalDateTime created;
}
