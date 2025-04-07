package example.day04._day04과제.model.repository;

import example.day04._day04과제.model.dto.TaskDto;
import example.day04._day04과제.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    // 네이티브 쿼리
    // [1] 비품 등록
    @Modifying
    @Query(value = "insert into day04task(name ,description,quantity) values(:name , :description, :quantity)"
            ,nativeQuery = true )
    int save2(String name , String description, String quantity);

    // [2] 전체 비품 조회
    @Query(value = "select * from day04task", nativeQuery = true)
    List<TaskEntity> findAll2();

    // [3] 개별 비품 조회
    @Query(value = "select * from day04task where id = :id", nativeQuery = true)
    Optional<TaskEntity> findById2(int id);

    // [4] 비품 수정
    @Modifying
    @Query(value = "update day04task set name = :name ,description =:description , quantity = :quantity where id = :id"
            , nativeQuery = true)
    int findById3(String name , String description, String quantity, int id);

    // [5] 비품 삭제
    @Modifying
    @Query(value = "delete from day04task where id = :id", nativeQuery = true )
    int deleteById2(int id);
}
