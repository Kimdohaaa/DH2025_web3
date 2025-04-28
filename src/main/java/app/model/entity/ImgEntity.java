package app.model.entity;

import app.model.dto.ImgDto;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "img")
@Getter
@Setter @Builder @ToString @NoArgsConstructor @AllArgsConstructor
public class ImgEntity extends BaseTime {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long ino ; // 이미지 식별번호

    @Column( nullable = false )
    private String iname; // 이미지 명
    // 단방향 연결 //
    // ProductEntity <-연결- ImgEntity
    @ManyToOne@JoinColumn(name = "pno")
    private ProductEntity productEntity; // ImgEntity 가 ProductEntity 를 참조
    
    // => ImgEntity 안에 ProductEntity 가 포함됨
    // ==> ImgEntity 를 통해 ProductEntity 에 접근 가능



}
