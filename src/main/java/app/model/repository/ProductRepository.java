package app.model.repository;

import app.model.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query(value = "select * from product where cno = :cno", nativeQuery = true)
    List<ProductEntity> nativeQuery1(long cno);

    // 2-3) JPQL : 자바가 만든 SQL 코드/메소드


    // [] 카테고리별 제품 키워드 검색
    // like : 포함
    // IS NULL : null 검사
    @Query( value = "SELECT * FROM product " +
            " WHERE ( :cno IS NULL OR :cno = 0 OR cno = :cno ) " + // java == null vs sql : IS NULL
            " AND ( :keyword IS NULL OR pname LIKE %:keyword% )" , nativeQuery = true)
    Page<ProductEntity> findBySearch(Long cno, String keyword, Pageable pageable);
}
