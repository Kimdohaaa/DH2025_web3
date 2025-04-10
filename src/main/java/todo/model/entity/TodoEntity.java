package todo.model.entity;

import todo.model.dto.TodoDto;
import jakarta.persistence.*;
import lombok.*;

@Entity // DB 의 테이블과 영속관계 매핑
@Table(name = "todo") // DB 테이블 명
@Data // 룸북 사용
@NoArgsConstructor // 룸북 사용 (디폴트 생성자)
@AllArgsConstructor // 룸북 사용 (풀 생성자)
@Builder // 빌더패턴 사용
@EqualsAndHashCode(callSuper=false)
public class TodoEntity extends BaseTime { // BaseTime Entity 상속받기

    @Id // PK 키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 적용
    private int id; // 할일 식별 번호

    private String title; // 할일 제목

    private String content; // 할일 내용

    private boolean done; // 할일 완료 여부

    // Entity -변환-> DTO 메소드
    public TodoDto toDto(){
        return TodoDto.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .done(this.done)
                .createAt(this.getCreateAt()) // BaseTime 에서 상속받은 생성 시점 변수
                .build();
    }
}
