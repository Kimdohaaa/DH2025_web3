package app.model.dto;

import app.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private  long pno;
    private String pname;
    private  String pcontent;
    private int pprcie;
    private int pview;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    ProductEntity toEntity(){
        return ProductEntity.builder()
                .pno(this.pno)
                .pname(this.pname)
                .pcontent(this.pcontent)
                .pprcie(this.pprcie)
                .pview(this.pview)
                .build();

    }
}
