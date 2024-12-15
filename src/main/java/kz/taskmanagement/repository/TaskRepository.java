package kz.taskmanagement.repository;

import kz.taskmanagement.entity.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.author.id = :userId")
    Page<Task> findTasksByAuthorId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.assignee.id = :assigneeId")
    Page<Task> findTasksByAssigneeId(Long assigneeId, Pageable pageable);

    @Modifying
    @Query(value = "DELETE t from Task t WHERE t.id = :id", nativeQuery = true)
    void deleteTaskById(Long id);
}
