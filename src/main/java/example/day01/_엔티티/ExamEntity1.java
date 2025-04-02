package example.day01._엔티티;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // @Entity : 해당 클래스를 엔티티로 사용
public class ExamEntity1 {

    @Id // @Id : 식별키(primary Key) => 엔티티로 사용할 클래스의 필수 요소
    private  int col1;
    private String col2;
    private double col3;
    // -> apllication.properties 에 설정된 JPA 를 통해 MySQL 에 테이블이 자동으로 생성됨
}
