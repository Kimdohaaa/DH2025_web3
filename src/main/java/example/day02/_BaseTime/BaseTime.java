package example.day02._BaseTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// BaseTime 클래스 : 모든 DB 테이블의 레코드 생성날짜와 수정날짜를 감지하는 엔티티
// => 관례적으로 기본적으로 생성함

@MappedSuperclass // @MappedSuperclass : 해당 클래스를 일반 클래스 엔티티가 아닌 슈퍼 클래스 엔티티로 사용
                  // => 해당 클래스에 프로젝트 시 공통적으로 사용할 로직 작성 후 자식 클래스에게 상속하기
                  // => 실질적으로 사용할 엔티티 클래스에서 해당 클래스를 상속 받아 사용하기
                  // => AppStart 클래스에 @EnableJpaAuditing 어노테이션을 통해 영속성 감지 기능을 활성화 시켜 사용
@EntityListeners(AuditingEntityListener.class) // @EntityListeners(AuditingEntityListener.class) : 해당 클래스의 멤버변수들을 JPA 감지 대상으로 지정
@Getter
public class BaseTime {

    // [1] @CreatedDate : 엔티티(레코드)의 영속(생성) 날짜와 시간 자동 주입
    @CreatedDate
    private LocalDateTime 생성날짜시간; // 회원가입날짜 , 제품등록일 , 주문일

    // [2] @LastModifiedDate : 엔티티(레코드)의 수정 날짜와 시간 자동 주입
    @LastModifiedDate
    private LocalDateTime 수정날짜시간;
}
