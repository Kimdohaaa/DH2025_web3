package example.day03._day03과제.entity;

import example.day03._day03과제.dto.StudentDto;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "day03student")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class StudentEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sno;

    private String sname;
private int cno;

    // FK 키
    @ManyToOne
    private CourseEntity courseEntity;

    // Dto 로 변환 메소드
    public StudentDto toDto(){
        return StudentDto.builder()
                .sno(this.sno)
                .sname(this.sname)
                .cno(this.cno)
                .build();
    }


}
