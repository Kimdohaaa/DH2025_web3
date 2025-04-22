package app.model.dto;

import app.model.entity.ImgEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImgDto {
    private  long ino;
    private String iname;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    ImgEntity toEntity(){
        return ImgEntity.builder()
                .ino(this.ino)
                .iname(this.iname)
                .build();
    }
}
