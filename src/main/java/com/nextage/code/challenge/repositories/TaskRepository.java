package com.nextage.code.challenge.repositories;

import com.nextage.code.challenge.enums.EStatus;
import com.nextage.code.challenge.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByStatus (EStatus status, Pageable pageable);
}
