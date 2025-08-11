package com.nextage.code.challenge.services;

import com.nextage.code.challenge.dto.TaskRequestDTO;
import com.nextage.code.challenge.dto.TaskResponseDTO;
import com.nextage.code.challenge.dto.TaskStatusUpdateDTO;
import com.nextage.code.challenge.enums.EStatus;
import com.nextage.code.challenge.mappers.TaskMapper;
import com.nextage.code.challenge.models.Task;
import com.nextage.code.challenge.repositories.TaskRepository;
import com.nextage.code.challenge.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Tarefa com id: " + taskId + " não encontrado"));
    }

    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Page<Task> getTasksByStatus(EStatus status, Pageable pageable) {
        return taskRepository.findByStatus(status, pageable);
    }

    public TaskResponseDTO save(TaskRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println(email);
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        var task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(EStatus.PENDENTE);
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        taskRepository.save(task);

        return taskMapper.toResponse(task);
    }

    public TaskResponseDTO updateTask(TaskRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Task task = taskRepository.findById(request.getId()).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        if (!task.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("Você não tem permissão para atualizar essa tarefa");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        Task updated = taskRepository.save(task);

        return taskMapper.toResponse(updated);
    }

    public TaskResponseDTO updateTaskStatus(Long id, TaskStatusUpdateDTO dto) {

        TaskRequestDTO taskResquestDTO = new TaskRequestDTO();
        taskResquestDTO.setId(id);
        taskResquestDTO.setStatus(dto.getStatus());

        var task = taskRepository.findById(taskResquestDTO.getId()).orElseThrow(() -> new RuntimeException("Tarefa com id: " + taskResquestDTO.getId() + " não encontrado"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!task.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("Você não tem permissão para modificar essa tarefa");
        }

        task.setStatus(taskResquestDTO.getStatus());

        Task updatedStatus = taskRepository.save(task);

        return taskMapper.toResponse(updatedStatus);
    }


    public void delete(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Tarefa com id: " + taskId + " não encontrado"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!task.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("Você não tem permissão para deletar essa tarefa");
        }

        taskRepository.delete(task);
    }
}
