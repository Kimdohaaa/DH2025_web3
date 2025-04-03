package example.day03._day03과제.dto;

import example.day03._day03과제.entity.StudentEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentDto {

    private int sno;
    private String sname;
    private int cno;

    // 엔티티로 변환 메소드
    public StudentEntity doEntity(){
        return StudentEntity.builder()
                .sno(this.sno)
                .sname(this.sname)
                .cno(this.cno)
                .build();
    }
}
