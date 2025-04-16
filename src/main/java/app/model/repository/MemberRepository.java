package app.model.repository;

import app.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    /*
    (1) JpaRepository 클래스를 상속받아 JPA 기본 CRUD 지원
    (2) 개발자가 정의한 쿼리메소드 지원 -> 메소드 명명 규칙
    (3) 개발자가 정의한 네이티브 쿼리 지원 -> SQL 직접 작성
    */

    // [1] 추상메소드를 이용하여 매개변수 email 을 통해 해당 엔티티 조회
    @Query(value = "select * from member where email = :email", nativeQuery = true)
    MemberEntity findByEmail(String email);
}
