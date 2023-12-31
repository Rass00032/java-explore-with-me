package ru.practicum.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.ParticipationRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Integer> {
    List<ParticipationRequest> findAllByRequester_Id(Integer userId);

    List<ParticipationRequest> findAllByEvent_Id(Integer eventId);

    List<ParticipationRequest> findAllByRequester_IdAndEvent_Id(Integer userId, Integer eventId);

    Boolean existsParticipationRequestByRequester_idAndEvent_Id(Integer userId, Integer eventId);

    Boolean existsParticipationRequestByIdInAndStatus(List<Integer> ids, ParticipationRequest.Status status);
}
