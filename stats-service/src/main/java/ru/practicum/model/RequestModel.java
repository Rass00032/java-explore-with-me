package ru.practicum.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "request")
public class RequestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String app; //"app": "ewm-main-service",

    @Column(nullable = false)
    private String uri;// "uri": "/events/1",

    @Column(nullable = false)
    private String ip; // "ip": "192.163.0.1",

    @Column(name = "times", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp; // "timestamp": "2022-09-06 11:00:23"
}

