package example.day04.controller;

import example.day04.model.dto.TodoDto;
import example.day04.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day04/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // [1] 등록
    @PostMapping
    public TodoDto todoSave(@RequestBody TodoDto todoDto){
        System.out.println("TodoController.todoSave");
        System.out.println("todoDto = " + todoDto);

        return todoService.todoSave(todoDto);

    }
    // [2] 전체 조회
    @GetMapping
    public List<TodoDto> todoFindAll(){
        System.out.println("TodoController.todoFindAll");

        return todoService.todoFindAll();
    }

    // [3] 개별 조회
    @GetMapping("/view")
    public TodoDto todoFindById(@RequestParam int id){
        System.out.println("TodoController.todo");
        System.out.println("id = " + id);

        return todoService.todoFindById(id);
    }

    // [4] 수정
    @PutMapping
    public TodoDto todoUpdate(@RequestBody TodoDto todoDto){
        System.out.println("TodoController.todoUpdate");
        System.out.println("todoDto = " + todoDto);

        return todoService.todoUpdate(todoDto);
    }

    // [5] 개별 삭제
    @DeleteMapping
    public boolean todoDelete(@RequestParam int id){
        System.out.println("TodoController.todoDelete");
        System.out.println("id = " + id);

        return todoService.todoDelete(id);
    }

    // [6] 전체 조회 (+ 페이징 처리)
    @GetMapping("/page")
    public List<TodoDto> todoFindByPage( // defaultValue = " " : 만약 매개변수 값이 존재하지 않으면 초기값 대입
            @RequestParam(defaultValue = "1") int page,  // page : 현재 조회 중인 페이지 번호 (초기값 : 1)
            @RequestParam(defaultValue = "3") int size){ // size : 페이지 1개 당 조회할 자료 개수 (초기값 : 3)

        System.out.println("TodoController.todoFindAllPage");

        return todoService.todoFindByPage(page, size);
    }
}
