package ru.practicum.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDto {
    private String app; //"app": "ewm-main-service",

    private String uri;// "uri": "/events/1",

    private String ip; // "ip": "192.163.0.1",

    private CharSequence timestamp; // "timestamp": "2022-09-06 11:00:23"
}
