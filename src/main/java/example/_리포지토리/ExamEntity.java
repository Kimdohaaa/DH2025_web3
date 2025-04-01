package example._리포지토리;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity // 해당 클래스를 엔티티로 사용
@Table(name = "student1") // DB 테이블 매핑
@Data // 롬복 적용
public class ExamEntity {

    // [1] 학번
    @Id // primary Key
    private String id;

    // [2] 이름
    @Column(nullable = false) // not null
    private String name;

    // [3] 국어점수
    @Column
    private int kor;

    // [4] 영어점수
    @Column
    private int eng;
}
