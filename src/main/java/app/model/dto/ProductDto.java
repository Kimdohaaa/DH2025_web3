package app.model.dto;

import app.model.entity.ImgEntity;
import app.model.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    // 제품 카테고리 번호
    private long cno ;
    // 업로드할 제품 이미지리스트
    private List<MultipartFile> files = new ArrayList<>();
    private String email;
    private String cname;
    // 사진명
    private List<String> images;

    public  ProductEntity toEntity(){
        return ProductEntity.builder()
                .pname(this.pname)
                .pcontent(this.pcontent)
                .pprcie(this.pprcie)
                .pview(this.pview)
                .build();
    }
    public static ProductDto toDto(ProductEntity productEntity){
        return ProductDto.builder()
                .pname(productEntity.getPname() )
                .pcontent(productEntity.getPcontent())
                .pprcie(productEntity.getPprcie())
                .pview(productEntity.getPview())
                .createAt(productEntity.getCreateAt())
                .updateAt(productEntity.getUpdateAt())
                .pno(productEntity.getPno())
                .email(productEntity.getMemberEntity().getEmail())
                .cname(productEntity.getCategoryEntity().getCname())
                .images(
                        productEntity.getImgEntityList().stream()
                                .map(ImgEntity::getIname)
                                .collect(Collectors.toList())
                )
                .build();
    }


}
