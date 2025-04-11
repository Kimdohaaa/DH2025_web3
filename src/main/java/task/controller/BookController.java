package task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import task.model.dto.BookDto;
import task.service.BookService;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/task/book")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BookController {

    private final BookService bookService;

    // [1] 책 추천 등록
    @PostMapping
    public BookDto saveBook (@RequestBody BookDto bookDto){
        System.out.println("BookController.saveBook");
        System.out.println("bookDto = " + bookDto);

        return bookService.saveBook(bookDto);
    }

    // [2] 책 추천 수정
    @PutMapping
    public boolean updateBook (@RequestBody BookDto bookDto){
        System.out.println("BookController.updateBook");
        System.out.println("bookDto = " + bookDto);

        return  bookService.updateBook(bookDto);
    }

    // [3] 책 추천 삭제
    @DeleteMapping
    public boolean deleteBook (@RequestParam int bno, @RequestParam String bpwd){
        System.out.println("BookController.deleteBook");
        System.out.println("bno = " + bno + ", bpwd = " + bpwd);

        return bookService.deleteBook(bno, bpwd);
    }

    // [4] 책 추천 전체 조회
    @GetMapping
    public List<BookDto> findAll (){
        System.out.println("BookController.findAll");

        return bookService.findAll();
    }

    // [5] 책 추천 상세 조회
    @GetMapping("/view")
    public BookDto findById(@RequestParam int bno){
        System.out.println("BookController.findById");
        System.out.println("bno = " + bno);

        return bookService.findById(bno);
    }
}
