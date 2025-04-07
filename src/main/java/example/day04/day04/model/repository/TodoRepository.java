package example.day04.day04.model.repository;

import example.day03._JPA연관관계.Board;
import example.day04.day04.model.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository <TodoEntity, Integer>{
    /*
    [JPA Repository 가 제공하는 CRUD 메소드]
    1. .save()
    2. .findById()
    3. .findAll()
    4. .deleteById()
    등등 ~~~

    [쿼리메소드(JPQL 을 이용한 메소드 이름 기반 자동 생성)]
    1. 반환타입 findBy필드명(타입 매개변수); : 해당 필드명을 조건으로 조회한 뒤 반환타입으로 리턴
        -> 주의점 : 카멜표기법 필수 !!! / 메소드 명명 규칙 (조회 시 findByXXX 형식 사용) !!!
    2. 반환타입 findBy필드명Containing(타입 매개변수); : 매개변수가 포함된 필드명 조회

    [네이티브쿼리(SQL 을 직접 작성)]
    1.@Query(value= "SQL 문" , nativeQuery = true)
      반환타입 메소드명(타입 매개변수);
        -> 쿼리메소드와 달리 메소드 명명 규칙을 지키지 않아도 무관
        -> SQL 문에 매개변수 사용 시 :변수명 형식으로 사용
        -> @Query 어노테이션은 Select 를 위한 어노테이션이므로 INSERT/UPDATE/DELETE 문 작성 시 @Modifying 어노테이션을 같이 주입하여 사용
    2. @Query(value= "SQL 문" , nativeQuery = true)
       반환타입 메소드명(타입 매개변수); : 매개변수가 포함된 필드명 조회 (== SQL 의 like 연산자 기능)

    */

    // [2] 쿼리메소드 : Spring JPA 에서 SQL 을 직접 작성하지 않고 메소드 이름으로 쿼리를 생성하는 것(카멜표기법 필수)
    // 2-1) findBy필드명(타입 매개변수) : 필드명을 조건으로 조회
    List<TodoEntity> findByTitle(String title); // findBy필드명(타입 매개변수) : 필드명을 조건으로 조회
                                                // == "Select * from todo where title = #{title}

    // 2-2) findBy필드명Containing : 매개변수가 포함된 필드명 조회
    List<TodoEntity> findByTitleContaining(String keyword); // findBy필드명Containing : 매개변수가 포함된 필드명 조회
                                                // == "Select * from todo where title like #{title}

    // 2-3) findBy필드명And필드명 : 두 조건을 조회 (And/ Or 사용)
    List<TodoEntity> findByTitleAndContent(String title, String content);

    // 2-4) existsBy필드명 : 조건을 조회한 뒤 조회성공 여부(존재여부)를 boolean 타입으로 반환
    boolean existsByTitle(String title);

    // 2-5) countBy필드명 : 조건에 맞는 엔티티 개수를 long 타입으로 반환
    long countByTitle(String title);

    // 2-6) deleteBy필드명 : 조건에 맞는 엔티티티 삭제
    void deleteByTitle(String title);

    // [3] 네이티브쿼리 : Spring JPA 에서 SQL 문법을 직접 작성하여 실행 하는 것
    // 3-1)
    @Query(value = "select * from todo where title = :title", nativeQuery = true)
    List<TodoEntity> findByTitleNative(String title); // 매개변수를 조건으로 조회

    // 3-2)
    @Query(value = "select * from todo where title like %:keyword%", nativeQuery = true)
    List<TodoEntity> findByTitleNativeSearch(String keyword); // 매개변수가 포함된 필드명 조회

    // 3-3) UPDATE
    @Modifying // @Query 어노테이션은 select 만 지원하므로 INSERT/UPDATE/DELETE 쿼리 작성 시 @Modifying 어노테이션 주입 필수
    @Query(value = "update todo set title = :title where id = :id", nativeQuery = true)
    int updateByNative(int id, String title);
    // 3-4) DELETE
    @Modifying // @Query 어노테이션은 select 만 지원하므로 INSERT/UPDATE/DELETE 쿼리 작성 시 @Modifying 어노테이션 주입 필수
    @Query(value = "delete from todo where title = :title", nativeQuery = true)
    int deleteByNative(String title);
}
