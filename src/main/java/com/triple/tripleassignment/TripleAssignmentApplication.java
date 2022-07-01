package com.triple.tripleassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // 생성 시간/수정 시간 자동 업데이트
@SpringBootApplication
public class TripleAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripleAssignmentApplication.class, args);
    }

}
