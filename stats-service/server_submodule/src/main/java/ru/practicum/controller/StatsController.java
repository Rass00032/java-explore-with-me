package ru.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.RequestDto;
import ru.practicum.dto.StatsDTO;
import ru.practicum.model.RequestModel;
import ru.practicum.service.StatsServiceImpl;

import java.util.List;

@Slf4j
@RestController
public class StatsController {
    @Autowired
    private StatsServiceImpl service;

    @PostMapping("/hit")
    public RequestModel creatingStats(@RequestBody RequestDto request) {
        return service.save(request);
    }

    @GetMapping("/stats")
    public List<StatsDTO> getStats(@RequestParam String start,
                                   @RequestParam String end,
                                   @RequestParam(required = false) List<String> uris,
                                   @RequestParam(defaultValue = "false") boolean unique) {
        log.info("\nЗапрос:\nstart {}\nend {}\nuri {}\nunique {}", start, end, uris, unique);

        return service.get(start, end, uris, unique);
    }


}
