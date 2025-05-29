package com.beyond.homs.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslConfig {

    // EntityManager를 주입받습니다.
    // @PersistenceContext는 Spring Data JPA가 EntityManager를 관리하고 주입하도록 합니다.
    @PersistenceContext
    private EntityManager entityManager;

    // JPAQueryFactory 빈을 등록합니다.
    // 스프링 컨테이너가 이 메서드를 호출하여 JPAQueryFactory 객체를 생성하고 빈으로 관리합니다.
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}