package com.example.gascalendar.mappers;

import com.example.gascalendar.dto.request.TaskCreateRequest;
import com.example.gascalendar.dto.request.TaskUpdateRequest;
import com.example.gascalendar.dto.response.TaskResponse;
import com.example.gascalendar.entity.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TasksMapper {
    TaskResponse toResponse(Task task);

    @Mapping(source = "columnId", target = "column")
    @Mapping(source = "date", target = "taskDate")
    @Mapping(source = "time", target = "taskTime")
    @Mapping(source = "type", target = "taskType")
    Task toCreateEntity(TaskCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "date", target = "taskDate")
    @Mapping(source = "time", target = "taskTime")
    void updateEntity(TaskUpdateRequest request, @MappingTarget Task task);
}
