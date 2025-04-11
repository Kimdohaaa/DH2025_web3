package task.model.entity;

import jakarta.persistence.*;
import lombok.*;
import task.model.dto.BookDto;

import java.time.LocalDateTime;

@Entity
@Table(name = "taskbook")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class BookEntity extends  BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int bno;

    private  String  bwriter;
    private  String  bname;
    private  String bcontent;
    private  String  bpwd;


    public BookDto toDto (){
        return BookDto.builder()
                .bno(this.bno)
                .bwriter(this.bwriter)
                .bcontent(this.bcontent)
                .bpwd(this.bpwd)
                .bname(this.bname)
                .createAt(this.getCreateAt())
                .updateAt(this.getUpdateAt())
                .build();
    }
}
