package com.lab.securing_project_tracker.repository;

import com.lab.securing_project_tracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findProjectByName(String name);
    @Query("SELECT p FROM Project p LEFT JOIN TaskEntity t ON p = t.project WHERE t.id IS NULL")
    List<Project> findProjectsWithoutTasks();
}
