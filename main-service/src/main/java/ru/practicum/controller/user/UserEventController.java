package ru.practicum.controller.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.request.EventRequestStatusUpdateRequest;
import ru.practicum.model.request.NewEventDto;
import ru.practicum.model.request.UpdateEventUserRequest;
import ru.practicum.model.response.EventFullDto;
import ru.practicum.model.response.EventRequestStatusUpdateResult;
import ru.practicum.model.response.EventShortDto;
import ru.practicum.model.response.ParticipationRequestDto;
import ru.practicum.service.EventService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserEventController {

    EventService eventService;

    @GetMapping
    public List<EventShortDto> getEvents(
            @PathVariable Integer userId,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return eventService.getEvents(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable Integer userId, @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping("{eventId}")
    public EventFullDto getEvent(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventService.getEvent(userId, eventId);
    }

    @PatchMapping("{eventId}")
    public EventFullDto changeEvent(
            @PathVariable Integer userId,
            @PathVariable Integer eventId,
            @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return eventService.changeEvent(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequests(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventService.getEventRequests(userId, eventId);
    }

    @PatchMapping("{eventId}/requests")
    public EventRequestStatusUpdateResult changeEventRequests(
            @PathVariable Integer userId,
            @PathVariable Integer eventId,
            @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return eventService.changeEventRequests(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
