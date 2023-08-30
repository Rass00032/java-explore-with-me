package ru.practicum.model.request;

import lombok.experimental.FieldDefaults;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationRequest {
    List<Integer> events;
    Boolean pinned;
    @Size(max = 50)
    String title;

}
