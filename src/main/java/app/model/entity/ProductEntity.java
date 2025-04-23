package app.model.entity;

import app.model.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity extends  BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long pno;

    @Column(nullable = false)
    private String pname;
    @Column(columnDefinition = "longtext")
    private  String pcontent;
    @Column
    @ColumnDefault("0") // 해당 필드의 디폴트값 지정
    private int pprcie;
    @Column
    @ColumnDefault("0")
    private int pview;

    // 단반향 연결 : 참조할 PK 필드가 존재하는 엔티티 필드 생성 //
    // MemberEntity / CategoryEntity <-연결- ProductEntity
    @ManyToOne
    @JoinColumn(name = "mno") // 실제 DB 에서는 "mno" 로 표현됨
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "cno") // 실제 DB 에서는 "cno" 로 표현됨
    private CategoryEntity categoryEntity;

    // => ProductEntity 안에 MemberEntity 와 CategoryEntity 가 포함됨
    // ==> ProductEntity 를 참조하여 두 엔티티에 접근 가능

    // 양방향 연결 //
    // ProductEntity <-연결-> ImgEntity / ReplyEntity
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<ImgEntity> imgEntityList = new ArrayList<>();


    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<ReplyEntity> replyEntityList = new ArrayList<>();




}
