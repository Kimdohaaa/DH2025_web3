package app.model.entity;

import app.model.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class MemberEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    private String  email;
    private String pwd;
    private String name;

    // 양방향 연결 : FK 엔티티를 여러개 가지므로 List 타입으로 선언 //
    // MemberEntity <-연결-> ProductEntity / ReplyEntity
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        // mappedBy = "memberEntity" : FK 엔티티에 연결된 필드명으로 지정
        // cascade = CascadeType.ALL : 제약 조건 모두 지정
        // fetch = FetchType.LAZY
    @ToString.Exclude // 양방향 설계 시 toString 룸북의 순환참조 방지 어노테이션 필수
    @Builder.Default // 엔티티 객체 생성 시 빌드 메소드를 사용하면 기본값 지정(ArrayList 를 기본값으로 지정)
    private List<ProductEntity> productEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<ReplyEntity> replyEntityList = new ArrayList<>();

    public MemberDto toDto(){
        return MemberDto.builder()
                .mno(this.mno)
                .email(this.email)
                // .pwd(this.pwd) // 보안을 위해 제외
                .name(this.name)
                .createAt(this.getCreateAt())
                .updateAt(this.getUpdateAt())
                .build();
    }
}
