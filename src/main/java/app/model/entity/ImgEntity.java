package app.model.entity;

import app.model.dto.ImgDto;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "img")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImgEntity extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long ino;

    @Column(nullable = false)
    private String iname;

    // 단방향 연결 //
    // ProductEntity <-연결- ImgEntity
    @ManyToOne
    @JoinColumn(name = "pno")
    private ProductEntity productEntity; // ImgEntity 가 ProductEntity 를 참조
    
    // => ImgEntity 안에 ProductEntity 가 포함됨
    // ==> ImgEntity 를 통해 ProductEntity 에 접근 가능

    ImgDto toDto(){
        return ImgDto.builder()
                .ino(this.ino)
                .iname(this.iname)
                .createAt(this.getCreateAt())
                .updateAt(this.getUpdateAt())
                .build();
    }

}
