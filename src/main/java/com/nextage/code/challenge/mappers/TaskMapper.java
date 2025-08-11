package com.nextage.code.challenge.mappers;

import com.nextage.code.challenge.dto.TaskResponseDTO;
import com.nextage.code.challenge.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskResponseDTO toResponse(Task task);
}
