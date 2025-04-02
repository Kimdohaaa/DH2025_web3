package example.day02._BaseTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // JPA 영속성 변화 감지 기능을 활성화하는 어노테이션
                   // => 해당 어노테이션을 통해 BaseTime 엔티티를 활성화 시킴
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);
    }
}
