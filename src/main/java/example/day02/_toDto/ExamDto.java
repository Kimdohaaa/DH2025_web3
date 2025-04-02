package example.day02._toDto;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Data;

// DTO 목적 : 서로 다른 계층/레이어 간의 이동 객체
// VO : 수정불가능한 객체(읽기모드 / Setter 없음)
// => 사용되는 계층 : 컨트롤러 레이어(view 와 controller 사이 / controller 와 service 사이)

@Data // 롬복 주입
@Builder // 빌더패턴 주입
public class ExamDto {

    // [1] 멤버변수
    private String id;
    private String title;
    private String price;

    // [2] 메소드
    // => Service 에서 사용하기 위해 DTO 를 Entity 로 변환하는 메소드 생성
    // => 관례적 메소드명 : toEntity
    public ExamEntity1 toEntity(){
        return  ExamEntity1.builder()
                .id(this.id)
                .title(this.title)
                .price(this.price)
                .build();
    }
}
