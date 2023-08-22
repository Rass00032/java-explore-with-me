package ru.practicum.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.dto.RequestDto;
import ru.practicum.dto.StatsDTO;
import ru.practicum.model.RequestModel;
import ru.practicum.repository.StatsRepository;

import java.util.List;


@Service
@AllArgsConstructor
public class StatsServiceImpl {
    private final StatsRepository statsRepository;
    private final ModelMapper mapper;


    public RequestModel save(RequestDto requestDto) {
        RequestModel requestModel = mapper.map(requestDto, RequestModel.class);
        statsRepository.save(requestModel);
        return requestModel;
    }

    public List<StatsDTO> get(String start, String end, List<String> uri, boolean isUnique) {

        if (isUnique) {
            return statsRepository.statisticsUniqueIp(start, end, uri);
        } else {
            return statsRepository.statisticsAllIp(start, end, uri);
        }
    }
}
