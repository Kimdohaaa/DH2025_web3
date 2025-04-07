package example.day04._day04과제.controller;

import example.day04._day04과제.model.dto.TaskDto;
import example.day04._day04과제.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day04/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // [1] 비품 등록
    @PostMapping
    public boolean save(@RequestBody TaskDto taskDto){
        System.out.println("TaskController.save");
        System.out.println("taskDto = " + taskDto);

        return taskService.save(taskDto);
    }

    // [2] 전체 비품 조회
    @GetMapping
    public List<TaskDto> findAll(){
        System.out.println("TaskController.findAll");

        return taskService.findAll();
    }

    // [3] 개별 비품 조회
    @GetMapping("/view")
    public TaskDto find(@RequestParam int id){
        System.out.println("TaskController.find");
        System.out.println("id = " + id);

        return taskService.find(id);
    }

    // [4] 비품 수정
    @PutMapping
    public boolean update(@RequestBody  TaskDto taskDto){
        System.out.println("TaskController.update");
        System.out.println("taskDto = " + taskDto);

        return taskService.update(taskDto);
    }

    // [5] 비품 삭제
    @DeleteMapping
    public boolean delete(@RequestParam int id){
        System.out.println("TaskController.delete");
        System.out.println("id = " + id);

        return  taskService.delete(id);
    }

    // [6] 페이징 처리가 추가된 전체 비품 조회
    @GetMapping("/page")
    public List<TaskDto> findByPage(@RequestParam(defaultValue =  "1") int page,
                                    @RequestParam(defaultValue = "3") int size){
        System.out.println("TaskController.findByPage");
        System.out.println("page = " + page + ", size = " + size);

        return taskService.findByPage(page,size);
    }
}
