package task.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task.model.entity.ReviewEntity;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    // [1] 리뷰삭제
    @Modifying
    @Query(value = "delete from taskreview where rno = :rno and rpwd = :rpwd", nativeQuery = true)
    int deleteReview(int rno, String rpwd);

    // [2] 책 별 추천 조회
    @Query(value = """
            select r.* from taskreview r join taskbook b on r.bno = b.bno where b.bname = :bname
            """,nativeQuery = true)
    List<ReviewEntity> findByBname(String bname);
}

