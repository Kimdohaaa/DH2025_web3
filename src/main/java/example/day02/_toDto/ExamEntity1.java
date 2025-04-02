package example.day02._toDto;
// Entity 목적 : DB 테이블의 영속(연결)된 객체
// => 사용되는 계층 : 서비스 레이어(비즈니스 로직)
// => 영속된 객체를 컨트롤러에서 접근할 경우 보안성 문제 때문에 컨트롤러에서는 DTO 를 사용함

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book1")
@Data // 롬복 주입
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더패턴 주입
public class ExamEntity1 {
    // [1] 멤버변수
    // 1) 도서번호(PK키)
    @Id
    private String id;

    // 2) 도서명
    private String title;

    // 3) 도서 가격
    private String price;


    // [2] 메소드
    // => Controller에서 사용하기 위해 Entity 를 DTO 로 변환하는 메소드 생성
    // => 관례적인 메소드명 : toDto
    public ExamDto toDto(){
        // this : 해당 메소드를 호출한 인스턴스 (본인을 의미)
        // super : 부모를 의미

        // 1) 일반 생성자를 사용한 방법
        // return new ExamDto(this.id, this.title, this.price);

        // 2) 빌더 패턴을 사용한 방법
        // 빌더패턴 : 규칙적인 생성자의 유연성을 보장하는 메소드를 제공하는 패턴
        return ExamDto.builder()
                .id(this.id)
                .title(this.title)
                .price(this.price)
                .build();
    }
}
