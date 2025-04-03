package example.day03._자바참조관계;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class Category {

    // 멤버변수 *선언* (생성이 아님 => 클래스는 실체가 아닌 설계도이기 때문)
    private int cno; // 카테고리번호 => int (기본타입)
    private String cname; // 카테고리명 => String (참조타입)

    // *** 양방향 *** //
    // 순환참조 방지를 위해 toString 에서 제외
    @ToString.Exclude // @ToString.Exclude(lombok 어노테이션) : 해당 필드를 toString 메소드에서 제외
    private List<Board> boardList; // 1 : n 양방향 참조

}
