package example.day03._day03과제.entity;

import example.day03._day03과제.dto.CourseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "day03course")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CourseEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  cno;

    private String cname;

    // 양방향
    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<StudentEntity> studentList = new ArrayList<>();


    // 메소드
    public CourseDto toDto(){
        return CourseDto.builder()
                .cno(this.cno)
                .cname(this.cname)
                .build();
    }
}
