package app.model.dto;

import app.model.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private int mno;
    private String email;
    private String pwd;
    private String  name;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(this.mno)
                .email(this.email)
                .pwd(this.pwd)
                .name(this.name)
                .build();
    };
}
