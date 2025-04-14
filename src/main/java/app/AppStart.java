package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication // 스프링부트 기초 설정 어노테이션
@EnableJpaAuditing // JPA 감사 기능 활성화 어노테이션
@EnableScheduling // 스케쥴링 기능 활성화 어노테이션
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class);
    }
}
