package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.entity.Category;
import ru.practicum.model.response.CategoryDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryDto dto);

    CategoryDto toCategoryDto(Category category);

    List<CategoryDto> toCategoryDtos(List<Category> categories);
}
