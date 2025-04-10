package todo.model.dto;

import todo.model.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
    private int id;
    private String title;
    private String content;
    private boolean done;

    private LocalDateTime createAt; // BaseTime Entity 에 있는 생성 날짜

    // DTO -변환-> Entity 메소드
    public TodoEntity toEntity(){
        return TodoEntity.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .done(this.done)
                .build();
    }
}
