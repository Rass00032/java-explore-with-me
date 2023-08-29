package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.Event;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.model.request.NewCompilationDto;
import ru.practicum.model.request.UpdateCompilationRequest;
import ru.practicum.model.response.CompilationDto;
import ru.practicum.storage.CompilationRepository;
import ru.practicum.storage.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompilationService {

    CompilationRepository compilationRepository;
    CompilationMapper compilationMapper;
    EventRepository eventRepository;

    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = compilationRepository.findAll(PageRequest.of(from / size, size))
                    .getContent();
        } else {
            compilations = compilationRepository
                    .findCompilationsByPinnedTrue(PageRequest.of(from / size, size)).getContent();
        }
        return compilationMapper.toCompilationDtos(compilations);
    }

    public CompilationDto getCompilation(Integer compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(NotFoundException::new);
        return compilationMapper.toCompilationDto(compilation);
    }

    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.toCompilation(newCompilationDto);
        if (newCompilationDto.getEvents() != null) {
            List<Event> events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
            compilation.setEvents(events);
        }
        compilationRepository.save(compilation);
        return compilationMapper.toCompilationDto(compilation);
    }

    public void deleteCompilation(Integer compId) {
        if (!compilationRepository.existsById(compId)) throw new NotFoundException("Подборка не найдена");
        compilationRepository.deleteById(compId);
    }

    public CompilationDto updateCompilation(Integer compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Подборка не найдена"));
        compilationMapper.updateCompilation(compilation, updateCompilationRequest);
        if (updateCompilationRequest.getEvents() != null) {
            List<Event> events = eventRepository.findAllByIdIn(updateCompilationRequest.getEvents());
            compilation.setEvents(events);
        }
        compilationRepository.save(compilation);
        return compilationMapper.toCompilationDto(compilation);
    }
}
