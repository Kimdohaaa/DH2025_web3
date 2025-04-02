package example.day01._day01과제;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public boolean post(TaskEntity entity){
        System.out.println("TaskService.post");
        System.out.println("entity = " + entity);

        taskRepository.save(entity);

        return true;
    }

    public List<TaskEntity> get(){
        return taskRepository.findAll();
    }

    @Transactional
    public boolean put(TaskEntity entity){
        Optional<TaskEntity> optionalTaskEntity = taskRepository.findById(entity.getId());

        if(optionalTaskEntity.isPresent()){

            TaskEntity result = optionalTaskEntity.get();

            result.setTitle(entity.getTitle());
            result.setWriter(entity.getWriter());
            result.setCompany(entity.getCompany());
            result.setDate(entity.getDate());

            return  true;
        }

        return  false;
    }

    public boolean delete(String id){
        taskRepository.deleteById(id);

        return true;
    }
}
