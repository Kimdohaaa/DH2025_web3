package app.model.dto;

import app.model.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private long cno;
    private String cname;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public CategoryEntity toEntity(){
        return CategoryEntity.builder()
                .cno(this.cno)
                .cname(this.cname)
                .build();
    }

    public static  CategoryDto toDto (CategoryEntity categoryEntity){
        return CategoryDto.builder()
                .cno(categoryEntity.getCno())
                .cname(categoryEntity.getCname())
                .build();
    }
}
