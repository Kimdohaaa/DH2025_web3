package example.day04._day04과제.service;

import example.day04._day04과제.model.dto.TaskDto;
import example.day04._day04과제.model.entity.TaskEntity;
import example.day04._day04과제.model.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    // [1] 비품 등록
    public TaskDto save(TaskDto taskDto){
        System.out.println("TaskService.save");
        System.out.println("taskDto = " + taskDto);

        TaskEntity taskEntity = taskDto.toEntity();

        TaskEntity saveEntity = taskRepository.save(taskEntity);

        if(saveEntity.getId() > 1){
            return saveEntity.toDto();
        }else {
            return null;
        }
    }

    // [2] 전체 비품 조회
    public List<TaskDto> findAll(){
        System.out.println("TaskService.findAll");

        return taskRepository.findAll().stream()
                .map(TaskEntity::toDto)
                .collect(Collectors.toList());
    }

    // [3] 개별 비품 조회
    public  TaskDto find(int id){
        System.out.println("TaskService.find");
        System.out.println("id = " + id);

        return taskRepository.findById(id)
                .map(TaskEntity::toDto)
                .orElse(null);
    }

    // [4] 비품 수정
    public TaskDto update(TaskDto taskDto){
        System.out.println("TaskService.update");
        System.out.println("taskDto = " + taskDto);

        Optional<TaskEntity> optional = taskRepository.findById(taskDto.getId());

        if(optional.isPresent()){
            TaskEntity taskEntity = optional.get();

            taskEntity.setName(taskDto.getName());
            taskEntity.setDescription(taskDto.getDescription());
            taskEntity.setQuantity(taskDto.getQuantity());

            return taskEntity.toDto();
        }

        return null;
    }

    // [5] 비품 삭제
    public boolean delete(int id){
        System.out.println("TaskService.delete");
        System.out.println("id = " + id);

        return taskRepository.findById(id)
                .map(
                        (entity) -> {
                            taskRepository.deleteById(id);
                            return  true;
                        }
                )
                .orElse(false);
    }

    // [6] 페이징 처리가 추가된 전체 비품 조회
    public List<TaskDto> findByPage(int page, int size){
        System.out.println("TaskService.findByPage");
        System.out.println("page = " + page + ", size = " + size);

        PageRequest pageRequest = PageRequest.of(page-1 , size , Sort.by(Sort.Direction.DESC, "id"));

        Page<TaskEntity> taskEntityPage = taskRepository.findAll(pageRequest);

        List<TaskDto> taskList = new ArrayList<>();

        for(int i = 0; i < taskEntityPage.getSize(); i++){
            TaskDto taskDto = taskEntityPage.getContent().get(i).toDto();
            taskList.add(taskDto);
        }

        return taskList;
    }

}
