package example.day02._BaseTime;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service // 비지니스로직 담당
@Transactional // 해당 클래스 메소드들이 SQL를 사용할경우 트랜잭션 적용
@RequiredArgsConstructor
public class BookService {

    private final  BookRepository  bookEntityRepository;

    // [1] 도서 등록
    public boolean post(  BookEntity bookEntity ){
        // 1. JPA 이용한 영속성/DB테이블(레코드) 넣기
        bookEntityRepository.save( bookEntity );
        return true;
    }

    // [2] 전체 조회
    public List<BookEntity> get(){
        // 2. JPA 이용한 영속된/DB테이블(레코드) 모두 조회
        return bookEntityRepository.findAll();
    }

    // [3] 도서 수정
    public boolean put(  BookEntity bookEntity ){
        System.out.println("BookService.put");
        System.out.println("bookEntity = " + bookEntity);

        // 1) JPA 를 이용하여 영속된 DB 테이블 레코드를 조회하고 존재하면 변수에 대입 존재하지 않으면 null 대입
        BookEntity entity = bookEntityRepository.findById( bookEntity.get도서번호() ).orElse( null );

        // 2) 존재하지 않으면 false 반환
        if( entity == null ) return false;

        // 3) 존재하면 영속 객체 수정
        entity.set도서명( bookEntity.get도서명() );
        entity.set저자( bookEntity.get저자() );
        entity.set출판연도( bookEntity.get출판연도() );
        entity.set출판사( bookEntity.get출판사() );
        return true;
    }

    // [4] 도서 삭제
    public boolean delete(  int 도서번호 ){
        // 4. JPA 이용한 영속된/DB테이블(레코드) 삭제
        bookEntityRepository.deleteById( 도서번호 );
        return true;
    }

}
