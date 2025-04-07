package example.day04._day04과제.model.entity;

import example.day04._day04과제.model.dto.TaskDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "day04task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class TaskEntity extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private  String description;

    private String quantity;


    public TaskDto toDto(){
        return TaskDto.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .quantity(this.quantity)
                .createAt(this.getCreateAt())
                .updateAt(this.getUpdateAt())
                .build();
    }

}
