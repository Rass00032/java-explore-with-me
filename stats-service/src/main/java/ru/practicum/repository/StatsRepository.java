package ru.practicum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.StatsDTO;
import ru.practicum.model.RequestModel;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<RequestModel, Long> {

    @Query(value = "SELECT app, uri, COUNT(*) AS hits " +
            "FROM request " +
            "WHERE uri IN(:uris) AND times BETWEEN CAST(:start AS timestamp) AND CAST(:end AS timestamp) " + // /events/2,'/events/1' '2022-09-03 11:00:22' '2022-09-12 12:00:23'
            "GROUP BY uri ",
            nativeQuery = true)
    List<StatsDTO> statisticsAllIp(String start, String end, List<String> uris);

    @Query(value = "SELECT app, uri, COUNT(DISTINCT IP) AS hits " +
            "FROM request " +
            "WHERE uri IN(:uris) AND times BETWEEN CAST(:start AS timestamp) AND CAST(:end AS timestamp) " + // /events/2,'/events/1' '2022-09-03 11:00:22' '2022-09-12 12:00:23'
            "GROUP BY uri ",
            nativeQuery = true)
    List<StatsDTO> statisticsUniqueIp(String start, String end, List<String> uris);

}
