package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.entity.Event;
import ru.practicum.entity.ParticipationRequest;
import ru.practicum.entity.User;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.model.response.ParticipationRequestDto;
import ru.practicum.storage.EventRepository;
import ru.practicum.storage.RequestRepository;
import ru.practicum.storage.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.practicum.entity.Event.State.CANCELED;
import static ru.practicum.entity.Event.State.PENDING;
import static ru.practicum.entity.ParticipationRequest.Status.CONFIRMED;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestService {

    RequestRepository requestRepository;
    UserRepository userRepository;
    EventRepository eventRepository;
    RequestMapper requestMapper;
    EventService eventService;

    public List<ParticipationRequestDto> getRequests(Integer userId) {
        if (!userRepository.existsById(userId)) throw new NotFoundException("Пользователь не найден");
        List<ParticipationRequest> requests = requestRepository.findAllByRequester_Id(userId);
        return eventService.toParticipationRequestDtos(requests);
    }

    public ParticipationRequestDto addRequest(Integer userId, Integer eventId) {
        if (requestRepository.existsParticipationRequestByRequester_idAndEvent_Id(userId, eventId)) {
            throw new ConflictException("Запрос уже существует");
        }
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        if (event.getConfirmedRequests() != null && event.getParticipantLimit() != 0
                && event.getConfirmedRequests().equals(event.getParticipantLimit())) {
            throw new ConflictException("The participant limit has been reached");
        }
        if (event.getInitiator().getId().equals(userId) || event.getState() == PENDING
                || event.getState() == CANCELED) {
            throw new ConflictException("Нельзя участвовать в своем или неопубликованном событии");
        }
        User requester = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        ParticipationRequest participationRequest = ParticipationRequest.builder()
                .created(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)).requester(requester).event(event).build();
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            participationRequest.setStatus((CONFIRMED));
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        } else participationRequest.setStatus((ParticipationRequest.Status.PENDING));
        requestRepository.save(participationRequest);
        ParticipationRequestDto participationRequestDto = requestMapper.toParticipationRequestDto(participationRequest);
        participationRequestDto.setRequester(participationRequest.getRequester().getId());
        participationRequestDto.setEvent(participationRequest.getEvent().getId());
        return participationRequestDto;
    }

    public ParticipationRequestDto cancelRequest(Integer userId, Integer requestId) {
        ParticipationRequest participationRequest = requestRepository
                .findById(requestId).orElseThrow(() -> new NotFoundException("Запрос не существует"));
        if (!participationRequest.getRequester().getId().equals(userId)) {
            throw new NotFoundException("Пользователь не является владельцем запроса");
        }
        participationRequest.setStatus(ParticipationRequest.Status.CANCELED);
        requestRepository.save(participationRequest);
        ParticipationRequestDto participationRequestDto = requestMapper.toParticipationRequestDto(participationRequest);
        participationRequestDto.setRequester(participationRequest.getRequester().getId());
        participationRequestDto.setEvent(participationRequest.getEvent().getId());
        return participationRequestDto;
    }
}
