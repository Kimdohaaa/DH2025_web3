package task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.model.entity.BookEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private int bno;
    private  String  bwriter;
    private  String  bname;
    private  String bcontent;
    private  String  bpwd;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public BookEntity toEntity(){
        return BookEntity.builder()
                .bno(this.bno)
                .bcontent(this.bcontent)
                .bname(this.bname)
                .bpwd(this.bpwd)
                .bwriter(this.bwriter)
                .build();
    }

}
