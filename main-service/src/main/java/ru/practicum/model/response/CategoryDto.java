package ru.practicum.model.response;

import lombok.experimental.FieldDefaults;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    Long id;
    @NotBlank
    @Size(max = 50)
    String name;
}
