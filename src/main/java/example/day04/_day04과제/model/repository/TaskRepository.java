package example.day04._day04과제.model.repository;

import example.day04._day04과제.model.dto.TaskDto;
import example.day04._day04과제.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
}
