package ru.practicum.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.experimental.FieldDefaults;
import lombok.*;
import ru.practicum.entity.Event;
import ru.practicum.model.request.LocationDto;

import java.time.LocalDateTime;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    String createdOn;
    String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    Integer id;
    UserShortDto initiator;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    String publishedOn;
    Boolean requestModeration;
    Event.State state;
    String title;
    Long views;
}
