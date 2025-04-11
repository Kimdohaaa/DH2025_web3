package task.model.entity;

import jakarta.persistence.*;
import lombok.*;
import task.model.dto.ReviewDto;

@Entity
@Table(name = "taskreview")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ReviewEntity extends  BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int rno;

    private  String rcontent;
    private String rpwd;
    private int bno;
    private String bname;

    public ReviewDto toDto(){
        return  ReviewDto.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rpwd(this.rpwd)
                .bno(this.bno)
                .bname(this.bname)
                .createAt(this.getCreateAt())
                .updateAt(this.getUpdateAt())
                .build();
    }
}
