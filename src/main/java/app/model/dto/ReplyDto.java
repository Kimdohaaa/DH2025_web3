package app.model.dto;

import app.model.entity.ReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDto {
    private long rno;
    private String rcontent;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    ReplyEntity toEntity(){
        return ReplyEntity.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .build();
    }
}
