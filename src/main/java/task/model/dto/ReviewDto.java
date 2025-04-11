package task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.model.entity.ReviewEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private  int rno;
    private  String rcontent;
    private String rpwd;
    private int bno;
    private String bname;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public ReviewEntity toEntity(){
        return ReviewEntity.builder()
                .bno(this.bno)
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rpwd(this.rpwd)
                .bname(this.bname)
                .build();
    }
}
