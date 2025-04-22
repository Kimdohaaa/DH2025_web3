package app.model.entity;

import app.model.dto.ReplyDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reply")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyEntity extends  BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rno;

    @Column(nullable = false, length = 255)
    private String rcontent;

    // 단방향 연결 //
    // MemberEntity / ProductEntity <-연결- ReplyEntity
    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity; // 작성자
    
    @ManyToOne
    @JoinColumn(name = "pno")
    private  ProductEntity productEntity;        
   
    // => ReplyEntity 안에 MemberEntity 와 ProductEnity 가 포함됨
    // ==> ReplyEntity 를 참조하여 두 엔티티에 접근 가능



    ReplyDto toDto(){
        return ReplyDto.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .createAt(this.getCreateAt())
                .updateAt(this.getUpdateAt())
                .build();
    }

}
