package example.day04._day04과제.model.dto;

import example.day04._day04과제.model.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {

    private int id;

    private String name;

    private  String description;

    private String quantity;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public TaskEntity toEntity (){
        return TaskEntity.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .quantity(this.quantity)
                .build();
    }

}
