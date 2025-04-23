package app.model.repository;

import app.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    // [1] JPA 가 제공하는 기본적인 함수 : save() / findAll() / delete() 등
    // [2] 직접 구현
    // 2-1) 쿼리메소드 ※ 명명규칙(카멜표기법) 필수 ※
    // 메소드명을 무조건 findByXXX() 로 선언해야함
    // * findByCno() : 불가 -> ProductEntity 에는 cno가 존재하지 않기때문
    // * findByPname() : 가능 -> ProductEntity 에는 pname 이 존재하기 때문
    List<ProductEntity> findByCategoryEntityCno(long cno);

    // 2-2) 네이티브쿼리
    @Query(value = "select * from product werer cno = :cno", nativeQuery = true)
    List<ProductEntity> nativeQuery1(long cno);

    // 2-3) JPQL : 자바가 만든 SQL 코드/메소드
}
