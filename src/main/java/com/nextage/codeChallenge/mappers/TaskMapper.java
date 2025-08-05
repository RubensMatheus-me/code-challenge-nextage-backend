package com.nextage.codeChallenge.mappers;

import com.nextage.codeChallenge.dto.TaskResponseDTO;
import com.nextage.codeChallenge.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskResponseDTO toResponse(Task task);
}
