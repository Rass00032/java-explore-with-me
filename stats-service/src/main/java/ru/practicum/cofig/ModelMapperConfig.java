package ru.practicum.cofig;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.dto.RequestDto;
import ru.practicum.model.RequestModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Converter<CharSequence, LocalDateTime> converter =
                mappingContext -> LocalDateTime.parse(mappingContext.getSource(), formatter);

        TypeMap<RequestDto, RequestModel> propertyMapper = mapper.createTypeMap(RequestDto.class, RequestModel.class);

        propertyMapper.addMappings(
                m -> m.using(converter).map(RequestDto::getTimestamp, RequestModel::setTimestamp)
        );
        return mapper;
    }


}
