package app.model.entity;

import app.model.dto.CategoryDto;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cno ;

    @Column(nullable = false, length = 100)
    private String cname;

    // 양방향연결 //
    // CategoryEntity <-연결-> ProductEntity
    @OneToMany( mappedBy = "categoryEntity" , cascade = CascadeType.ALL , fetch = FetchType.LAZY )
    @ToString.Exclude @Builder.Default
    private List<ProductEntity> productEntityList = new ArrayList<>();

}
