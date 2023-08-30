package ru.practicum.mapper;

import org.mapstruct.*;
import ru.practicum.entity.Event;
import ru.practicum.model.request.NewEventDto;
import ru.practicum.model.request.UpdateEventAdminRequest;
import ru.practicum.model.request.UpdateEventUserRequest;
import ru.practicum.model.response.EventFullDto;
import ru.practicum.model.response.EventShortDto;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class EventMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract Event toEvent(NewEventDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract void updateEvent(@MappingTarget Event entity, UpdateEventAdminRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "eventDate", source = "dto.eventDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    public abstract void updateEvent(@MappingTarget Event entity, UpdateEventUserRequest dto);

    public abstract EventFullDto toEventFullDto(Event event);

    public abstract List<EventShortDto> toEventDtos(List<Event> events);

    public abstract List<EventFullDto> toEventFullDtos(List<Event> events);

    public abstract List<EventShortDto> toEventShortDtos(List<Event> events);
}
