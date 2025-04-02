package example.day02._BaseTime;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional // 해당 클래스의 메소드가 SQL 사용 시 트랜젝션 적용
public class BookService {

    private final  BookRepository bookRepository;

    // [1] 도서 등록
    public boolean post(BookEntity entity){
        System.out.println("BookService.post");
        System.out.println("entity = " + entity);

        // 1) JPA 를 이용하여 영속성 부여(DB 테이블에 Insert)
        bookRepository.save(entity);

        return true;
    }

    // [2] 전체 조회
    public List<BookEntity> get(){
        System.out.println("BookService.get");

        // 1) JPA 를 이용하여 영속된 DB 테이블 레코드를 모두 조회
        return bookRepository.findAll();
    }

    // [3] 도서 수정
    public boolean put(BookEntity entity){
        System.out.println("BookService.put");
        System.out.println("entity = " + entity);

        // 1) JPA 를 이용하여 영속된 DB 테이블 레코드를 조회하고 존재하면 변수에 대입 존재하지 않으면 null 대입
        BookEntity findEntity
                = bookRepository.findById(entity.getBno()).orElse(null);

        // 2) 존재하지 않으면 false 반환
        if(findEntity == null){
            return  false;
        }

        // 3) 존재하면 영속 객체 수정
        findEntity.setBno(entity.getBno());
        findEntity.setBname(entity.getBname());
        findEntity.setWriter(entity.getWriter());
        findEntity.setBcompany(entity.getBcompany());
        findEntity.setYear(entity.getYear());

        return true;
    }

    // [4] 도서 삭제
    public boolean delete(int bno){
        System.out.println("BookService.delete");
        System.out.println("bno = " + bno);

        // 1) 영속된 객체 삭제
        bookRepository.deleteById(bno);

        return true;
    }

}
