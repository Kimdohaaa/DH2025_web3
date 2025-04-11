package task.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task.model.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    // [1] 책 추천 수정
    @Modifying
    @Query(value = """
            update taskbook set bname = :bname, bwriter = :bwriter, 
            bcontent = :bcontent where bno = :bno 
            """, nativeQuery = true)
    int updateBook(String bname, String bwriter, String  bcontent, int bno);

    // [2] 책 추천 삭제
    @Modifying
    @Query(value = "delete from taskbook where bno = :bno", nativeQuery = true)
    int deleteBook(int bno);
}
