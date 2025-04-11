package task.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import task.model.dto.BookDto;
import task.model.entity.BookEntity;
import task.model.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private  final BookRepository bookRepository;

    // [1] 책 추천 등록
    public BookDto saveBook (BookDto bookDto){

        System.out.println("BookService.saveBook");
        System.out.println("bookDto = " + bookDto);

        BCryptPasswordEncoder BMpwd = new BCryptPasswordEncoder();

        String hashedPassWord = BMpwd.encode(bookDto.getBpwd());

        bookDto.setBpwd(hashedPassWord);

        BookEntity bookEntity = bookDto.toEntity();

        BookEntity saveEntity = bookRepository.save(bookEntity);

        if(saveEntity.getBno() > 1){
            return  saveEntity.toDto();
        }
        return  null;
    }

    // [2] 책 추천 수정
    public boolean updateBook(BookDto bookDto){
        Optional<BookEntity> optionalBook = bookRepository.findById(bookDto.getBno());
        if (optionalBook.isEmpty()) return false;

        BookEntity original = optionalBook.get();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(bookDto.getBpwd(), original.getBpwd())) {
            return false; // 비밀번호 틀림
        }

        int result = bookRepository.updateBook(
                bookDto.getBname(),
                bookDto.getBwriter(),
                bookDto.getBcontent(),
                bookDto.getBno()
        );

        return result > 0;
    }


    // [3] 책 추천 삭제
    public boolean deleteBook ( int bno, String bpwd){
        System.out.println("BookService.deleteBook");
        System.out.println("bno = " + bno + ", bpwd = " + bpwd);

        int result = bookRepository.deleteBook(bpwd, bno);

        if(result > 0){
            return  true;
        }

        return  false;
    }

    // [4] 책 추천 전체 조회
    public List<BookDto> findAll (){
        System.out.println("BookService.findAll");

        return bookRepository.findAll().stream()
                .map(BookEntity::toDto)
                .collect(Collectors.toList());
    }

    // [5] 책 추천 상세 조회
    public BookDto findById(int bno){
        System.out.println("BookService.findById");
        System.out.println("bno = " + bno);

        return bookRepository.findById(bno)
                .map(BookEntity::toDto)
                .orElse(null);
    }

}
