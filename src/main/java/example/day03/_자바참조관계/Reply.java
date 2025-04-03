package example.day03._자바참조관계;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Reply {

    // 멤버변수 선언
    private int rno;
    private String rcontent;

    private Board board; // Reply 이 Board 를 참조함
                         // board 멤버변수를 통해 Board 의 멤버변수 조회 가능

    // Board 이 PK , Reply 이 Fk : 단방향 참조
    // => Board 는 Reply 을 모름

}
