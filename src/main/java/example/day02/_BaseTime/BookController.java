package example.day02._BaseTime;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/day02/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // [1] 도서 등록
    @PostMapping
    public boolean post(@RequestBody BookEntity entity){
        System.out.println("entity = " + entity);
        System.out.println("BookController.post");

        return  bookService.post(entity);
    }

    // [2] 전체 조회
    @GetMapping
    public List<BookEntity> get(){
        System.out.println("BookController.get");

        return  bookService.get();
    }

    // [3] 도서 수정
    @PutMapping
    public boolean put(@RequestBody BookEntity entity){
        System.out.println("BookController.put");
        System.out.println("entity = " + entity);

        return bookService.put(entity);
    }

    // [4] 도서 삭제
    @DeleteMapping
    public boolean delete(@RequestParam int bno){
        System.out.println("BookController.delete");
        System.out.println("bno = " + bno);

        return bookService.delete(bno);
    }
}
