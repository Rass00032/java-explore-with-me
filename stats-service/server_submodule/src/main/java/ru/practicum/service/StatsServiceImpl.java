package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.dto.RequestDto;
import ru.practicum.dto.StatsDTO;
import ru.practicum.model.RequestModel;
import ru.practicum.repository.StatsRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StatsServiceImpl {
    private final StatsRepository statsRepository;
    private final ModelMapper mapper;

    @Transactional
    public RequestModel save(RequestDto requestDto) {
        RequestModel requestModel = mapper.map(requestDto, RequestModel.class);
        statsRepository.save(requestModel);
        log.info("Запрос {} добавлен дата: {}", requestModel.getUri(), requestModel.getTimestamp());
        return requestModel;
    }


    public List<StatsDTO> get(String start, String end, List<String> uri, boolean isUnique) {
        log.info("Получение запросов");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTimeStart = LocalDateTime.parse(start, formatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(end, formatter);

        if (dateTimeEnd.isBefore(dateTimeStart)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Дата окончания не может быть до даты начала");
        }

        if (uri == null) {
            if (isUnique) {
                return statsRepository.statisticsUniqueIpNotUri(start, end);
            } else {
                return statsRepository.statisticsAllIpNotUri(start, end);
            }
        }

        if (isUnique) {
            return statsRepository.statisticsUniqueIp(start, end, uri);
        } else {
            return statsRepository.statisticsAllIp(start, end, uri);
        }
    }
}
