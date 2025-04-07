package example.day04.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Data
@MappedSuperclass
public class BaseTime  {

    @CreatedDate // 영속성 생성 시점
    private LocalDateTime createAt;

    @LastModifiedDate // 영속성 수정 시점
    private LocalDateTime updateAt;
}
