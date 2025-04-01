package example._day01과제;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day01/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PostMapping
    public boolean post(@RequestBody TaskEntity entity){
        return taskService.post(entity);
    }

    @GetMapping
    public List<TaskEntity> get (){
        return taskService.get();
    }

    @PutMapping
    public boolean put(@RequestBody TaskEntity entity){
        return taskService.put(entity);
    }

    @DeleteMapping
    public boolean delete(@RequestParam String id){
        return taskService.delete(id);
    }


}
