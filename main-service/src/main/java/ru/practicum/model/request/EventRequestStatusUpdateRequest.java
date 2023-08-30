package ru.practicum.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.entity.ParticipationRequest.Status;

import java.util.List;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {

    List<Integer> requestIds;
    Status status;
}
