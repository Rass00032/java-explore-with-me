package ru.practicum.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.RequestDto;
import ru.practicum.dto.StatsDTO;
import ru.practicum.model.RequestModel;
import ru.practicum.service.StatsServiceImpl;

import java.util.List;

@RestController
public class StatsController {
    @Autowired
    private StatsServiceImpl service;
    @Autowired
    private ModelMapper mapper;


    @PostMapping("/hit")
    public RequestModel creatingStats(@RequestBody RequestDto request) {
        return service.save(request);
    }

    @GetMapping("/stats")
    public List<StatsDTO> getStats(@RequestParam String start,
                                   @RequestParam String end,
                                   @RequestParam List<String> uris,
                                   @RequestParam boolean unique) {
        System.out.println("start " + start);
        System.out.println("end " + end);
        System.out.println("uri " + uris);
        System.out.println("unique " + unique);
        return service.get(start, end, uris, unique);
    }


}
