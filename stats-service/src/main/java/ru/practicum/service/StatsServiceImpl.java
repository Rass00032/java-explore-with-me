package ru.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.dto.RequestDto;
import ru.practicum.dto.StatsDTO;
import ru.practicum.model.RequestModel;
import ru.practicum.repository.StatsRepository;

import javax.transaction.Transactional;
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
        if (isUnique) {
            return statsRepository.statisticsUniqueIp(start, end, uri);
        } else {
            return statsRepository.statisticsAllIp(start, end, uri);
        }
    }
}
