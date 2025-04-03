package example.day03._JPA연관관계;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "day03board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int bno; // 게시물 번호
    private  String bname; // 게시물명
    private String bcontent; // 게시물 내용

    // 단방향 연결을 위해 FK 필드 선언 : Category 참조
    @ManyToOne // 해당 필드를 FK 필드로 지정하는 어노테이션 (1 : n)
    private Category category;

    // 양방향 연결
    @ToString.Exclude // 순환참조방지를 위해 해당 필드를 toString 에서 제외
    @Builder.Default  // 빌더패턴 사용 시 초기값으로 new ArrayList<>() 사용
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
                                    // mappedBy = "단방향의멤버변수" : 양방향 참조테이블은 자바에서만 참조되도록 지정 (DB 에서는 참조 X)
                                    // new ArrayList<>() 로 메모리 할당
    private List<Reply> replyList = new ArrayList<>();

}
