package example.day03._day03과제.dto;

import example.day03._day03과제.entity.CourseEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CourseDto {

    private int cno;
    private String cname;

    // 엔티변환 메소드
    public CourseEntity toEntity(){
        return CourseEntity.builder()
                .cno(this.cno)
                .cname(this.cname)
                .build();
    }
}
