package example._엔티티;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity // 해당 클래스를 DB 테이블과 매핑
@Table(name = "exmaple2") // 테이블명 정의 (생략 시 클래스 명으로 정의됨)
public class ExmaEntity2 {

    @Id // 해당 멤버변수를 primary key 로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 멤버변수에 auto_increment 지원
    private  long id;

    @Column(nullable = true, unique = false) //
    private  String col1;
    @Column(nullable = false , unique = true) //
    private  String col2;

    @Column(columnDefinition = "varchar(30)")
    private String col3;

    @Column(name = "제품명", length = 30, insertable = false, updatable = false)
    private String col4;

    @Column
    private LocalDate col5;  // date 타입 지정

    @Column
    private LocalDateTime col6; // datetime 타입 지정
}
