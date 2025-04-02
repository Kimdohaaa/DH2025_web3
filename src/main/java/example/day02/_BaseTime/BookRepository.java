package example.day02._BaseTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
        // 제네릭 타입은 기본형(Int) 사용 불가 참조형(Integer) 사용
}
