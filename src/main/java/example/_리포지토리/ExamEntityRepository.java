package example._리포지토리;

// Repository : 엔티티(테이블)를 조작(DML)하기 위한 인터페이스 //
// JpaRepository : 기본적인 CRUD 제공 (insert / select / update / delete)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 스프링 컨테이너에 빈 등록
public interface ExamEntityRepository // ★ JPA Repository 상속받기 ★
        extends JpaRepository<ExamEntity, String> { // ★ JpaRepository<조작할엔티티클래스명, 해당엔티티의 ID 타입> ★


}
