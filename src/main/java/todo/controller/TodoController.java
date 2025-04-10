package todo.controller;

import todo.model.dto.TodoDto;
import todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day04/todos")
@RequiredArgsConstructor
@CrossOrigin("*") // 플러터와 통신을 위해 Cors 허용
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

    // [*] 제목 검색 조회 (입력한 값과 일치한 제목 조회)
    @GetMapping("/search1")
    public List<TodoDto> search1 (@RequestParam  String title){
        System.out.println("TodoService.search1");
        System.out.println("title = " + title);

        return todoService.search1(title);

    }
    // [*] 제목 검색 조회 (입력한 값이 포함된 제목 조회)
    @GetMapping("/search2")
    public List<TodoDto> search2(@RequestParam String keyword){
        System.out.println("TodoController.search2");
        System.out.println("keyword = " + keyword);

        return todoService.search2(keyword);
    }
}
