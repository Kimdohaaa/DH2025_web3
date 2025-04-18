package example.day03._자바참조관계;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
public class Board {

    // 멤버변수 선언
    private int bno; // 게시물 번호
    private String bname; // 게시물명
    private String bcontent; // 게시물 내용

    private Category category; // Board 가 Category 를 참조함
                               // category 멤버변수를 통해 Category 의 멤버변수 조회 가능

    // Category 이 PK , Board 이 Fk : 단방향 참조
    // => Category 는 Board 를 모름

    // *** 양방향 *** //
    // 순환참조 방지를 위해 toString 에서 제외
    @ToString.Exclude // @ToString.Exclude(lombok 어노테이션) : 해당 필드를 toString 메소드에서 제외
    private List<Reply> replyList; // 1 : n 양방향 참조

}
