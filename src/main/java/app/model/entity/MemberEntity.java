package app.model.entity;

import app.model.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
public class MemberEntity extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    private String  email;
    private String pwd;
    private String name;

    public MemberDto toDto(){
        return MemberDto.builder()
                .mno(this.mno)
                .email(this.email)
                // .pwd(this.pwd) // 보안을 위해 제외
                .name(this.name)
                .createAt(this.getCreateAt())
                .updateAt(this.getUpdateAt())
                .build();
    }
}
