package example.day03._day03과제;

import example.day03._day03과제.dto.CourseDto;
import example.day03._day03과제.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day03/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // [1] 과정 등록
    @PostMapping
    public boolean post1(@RequestBody CourseDto courseDto){
        return taskService.post1(courseDto);
    }
    // [2] 과정 전체 조회
    @GetMapping
    public List<CourseDto> get1(){
        return  taskService.get1();
    }
    // [3] 특정 과정에 수강생 드록
    @PostMapping("/student")
    public boolean post2( @RequestBody StudentDto studentDto){
        return taskService.post2(studentDto);
    }
    // [4] 특정 과정에 수강생 전체 조회
    @GetMapping("/student")
    public List<StudentDto> get2(@RequestParam int cno){
        return taskService.get2(cno);
    }
}
