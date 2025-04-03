package example.day03._JPA연관관계;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity // 해당 클래스를 DB 와 영속관계로 사용
@Table(name = "day03category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno ; // 카테고리 번호
    private String cname; // 카테고리명

    // 양방향 연결
    @ToString.Exclude // 순환참조방지를 위해 해당 필드를 toString 에서 제외
    @Builder.Default  // 빌더패턴 사용 시 초기값으로 new ArrayList<>() 사용
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
                         // 해당 필드를 통해 양방향 연결을 해주는 어노테이션 (1 : n)
                         // mappedBy = "단방향의멤버변수" : 양방향 참조테이블은 자바에서만 참조되도록 지정 (DB 에서는 참조 X)
                                    // new ArrayList<>() 로 메모리 할당
    private List<Board> boardList = new ArrayList<>();
}
/*

[영속성 제약 조건 옵션]
1. cascade : @OneToMany(mappedBy= "참조할멤버변수명' , cascade = CascadeType.XXX)
    1) CascadeType.ALL : PK 가 삭제/수정/저장(remove/merge/persist) 시 FK 도 삭제/수정/저장(remove/merge/persist)
    2) CascadeType.REMOVE : PK 가 삭제(remove)되면 FK 도 삭제(remove)
    3) CascadeType.MERGE : PK 가 수정(merge)되면 FK 도 수정(merge)
    4) CascadeType.PERSIST : PK 가 저장(persist)되면 FK 도 저장(persist)
    5) CascadeType.DETACH : PK 가 영속해제(detach)되면 FK 도 영속해제(detach)
    5) CascadeType.REFRESH : PK 가 새로운 값을 불러오면(refresh) FK 도 새로운 값을 불러옴(refresh)

2. fetch(상태 활성화) : @OneToMany(mappedBy= "참조할멤버변수명' , fetch = FetchType.XXX) ★ @ManyToOne 에서도 사용 가능 ★
    1) FetchType.EAGER(기본값) : 즉시 로딩 => 해당 엔티티를 .findXXX(조회) 할 때 참조되는 모든 객체를 즉시 불러옴
        -> 특징 : 초기 로딩이 느림 => 불필요한 엔티티까지 모두 가져오기 때문에 메모리(로드) 기능 저하
        -> 소규모에서 주로 사용
    2) FetchType.LAZY : 지연 로딩 => 해당 엔티티를 .findXXX(조회)할 때 참조되는 객체를 불러오지 않고 .getXXX() 을 참조 할 때 불러옴
        -> 특징 : 초기 로딩이 빠름 => 메모리 사용량을 적절하게 사용하기 때문에 성능 최적화 가능
        -> 대규모에서 주로 사용

*/
