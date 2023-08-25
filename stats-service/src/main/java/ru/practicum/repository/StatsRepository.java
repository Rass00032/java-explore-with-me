package ru.practicum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.dto.StatsDTO;
import ru.practicum.model.RequestModel;

import java.util.List;

public interface StatsRepository extends JpaRepository<RequestModel, Long> {

    @Query("SELECT new ru.practicum.dto.StatsDTO(r.app, r.uri, COUNT(*)) " +
            "FROM RequestModel r " +
            "WHERE r.uri IN(:uris) AND r.timestamp BETWEEN CAST(:start AS timestamp) AND CAST(:end AS timestamp) " +
            "GROUP BY r.uri, r.app " +
            "ORDER BY COUNT(*) DESC")
    List<StatsDTO> statisticsAllIp(String start, String end, List<String> uris);

    @Query("SELECT new ru.practicum.dto.StatsDTO(r.app, r.uri, COUNT(DISTINCT r.ip)) " +
            "FROM RequestModel r " +
            "WHERE r.uri IN(:uris) AND r.timestamp BETWEEN CAST(:start AS timestamp) AND CAST(:end AS timestamp) " +
            "GROUP BY r.uri, r.app " +
            "ORDER BY COUNT(DISTINCT r.ip) DESC")
    List<StatsDTO> statisticsUniqueIp(String start, String end, List<String> uris);

    @Query("SELECT new ru.practicum.dto.StatsDTO(r.app, r.uri, COUNT(*)) " +
            "FROM RequestModel r " +
            "WHERE r.timestamp BETWEEN CAST(:start AS timestamp) AND CAST(:end AS timestamp) " +
            "GROUP BY r.uri, r.app " +
            "ORDER BY COUNT(*) DESC")
    List<StatsDTO> statisticsAllIpNotUri(String start, String end);

    @Query("SELECT new ru.practicum.dto.StatsDTO(r.app, r.uri, COUNT(DISTINCT r.ip)) " +
            "FROM RequestModel r " +
            "WHERE r.timestamp BETWEEN CAST(:start AS timestamp) AND CAST(:end AS timestamp) " +
            "GROUP BY r.uri, r.app " +
            "ORDER BY COUNT(DISTINCT r.ip) DESC")
    List<StatsDTO> statisticsUniqueIpNotUri(String start, String end);

}
