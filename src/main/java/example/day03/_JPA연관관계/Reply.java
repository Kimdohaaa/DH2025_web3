package example.day03._JPA연관관계;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "day03reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int rno; // 댓글 번호
    private String rcontent; // 댓글 내용

    // 단방향 연결을 위해 FK 필드 선언 : Board 참조
    @ManyToOne // 해당 필드를 FK 필드로 지정하는 어노테이션 (1 : n)
    private Board board;
}
