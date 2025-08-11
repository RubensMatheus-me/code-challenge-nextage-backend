package com.nextage.code.challenge.controllers;

import com.nextage.code.challenge.dto.TaskRequestDTO;
import com.nextage.code.challenge.dto.TaskResponseDTO;
import com.nextage.code.challenge.dto.TaskStatusUpdateDTO;
import com.nextage.code.challenge.enums.EStatus;
import com.nextage.code.challenge.mappers.TaskMapper;
import com.nextage.code.challenge.models.Task;
import com.nextage.code.challenge.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    @Operation(summary = "Lista todas as tarefas com filtro e ordenação")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<Page<TaskResponseDTO>> getTasks(
            @RequestParam(required = false) EStatus status,
            @RequestParam(defaultValue = "0" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<Task> tasks = (status != null) ? taskService.getTasksByStatus(status, pageable) : taskService.getAllTasks(pageable);

        Page<TaskResponseDTO> dtoPage = tasks.map(taskMapper::toResponse);

        if(dtoPage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(dtoPage);
    }

    @Operation(summary = "Encontrar uma tarefa por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao encontrar Tarefa")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @Operation(summary = "Adicionar uma tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa adicionada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao adicionar Tarefa")
    })
    @PostMapping
    public ResponseEntity<TaskResponseDTO> add(@RequestBody @Valid TaskRequestDTO task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @Operation(summary = "Editar uma tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa editada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao editar uma Tarefa")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable long id,@RequestBody TaskRequestDTO task) {
        task.setId(id);
        return ResponseEntity.ok(taskService.updateTask(task));
    }

    @Operation(summary = "Mudar status de uma tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da Tarefa alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao alterar status de uma Tarefa")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateStatus(@PathVariable long id, @RequestBody TaskStatusUpdateDTO dto) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, dto));
    }

    @Operation(summary = "Apagar uma tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa apagada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao apagar uma Tarefa")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable("id") Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
