package ru.practicum.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static ru.practicum.entity.ParticipationRequest.Status;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    Integer id;
    LocalDateTime created;
    Integer event;
    Integer requester;
    Status status;
}
