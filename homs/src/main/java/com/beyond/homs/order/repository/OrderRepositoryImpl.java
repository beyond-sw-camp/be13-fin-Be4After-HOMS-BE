package com.beyond.homs.order.repository;

import com.beyond.homs.order.data.OrderSearchOption;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.beyond.homs.order.entity.QOrder.order;
import static com.beyond.homs.company.entity.QCompany.company;
import static com.beyond.homs.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    
    // Querydsl 쿼리를 생성하는 핵심 클래스
    // 내부적으로 EntityManager를 사용하여 데이터베이스에 접근
    private final JPAQueryFactory queryFactory;

    // 동적 검색 조건 메서드
    private BooleanExpression searchOptions(String keyword, OrderSearchOption option) {
        // 검색 조건이 없으면 null 반환
        if (option == null || keyword == null) {
            return null;
        }

        if (option == OrderSearchOption.ORDER_CODE){
            return order.orderCode.contains(keyword); // 코드 검색
        } else if (option == OrderSearchOption.COMPANY_NAME){
            return company.companyName.contains(keyword); // 회사 검색
        }

        return null; // 일치하는 옵션 없으면 null
    }

    @Override
    public Page<OrderResponseDto> findOrders(OrderSearchOption option, String keyword, Pageable pageable) {
        List<OrderResponseDto> content = queryFactory // JPAQueryFactory 사용
                // select문 시작
                .select(Projections.constructor(OrderResponseDto.class, // DTO 인트턴스 직접 생성
                        order.orderId,
                        order.orderCode,
                        company.companyName,
                        order.orderDate,
                        order.dueDate,
                        order.approved,
                        order.parentOrder.orderId,
                        order.rejectReason,
                        order.orderStatus
                ))
                .from(order)
                .leftJoin(order.user,user)
                .leftJoin(user.company,company)
                .where(
                        searchOptions(keyword, option) // 동적 검색 조건
                )
                .orderBy(order.orderId.desc()) // 정렬
                .offset(pageable.getOffset()) // 페이징 시작 오프셋
                .limit(pageable.getPageSize()) // 페이지 크기
                .fetch(); // 실제 쿼리 실행 및 결과 리스트 반환

        // 총 개수 쿼리
        JPAQuery<Long> totalCount = queryFactory
                .select(order.count()) // COUNT 쿼리
                .from(order)
                .leftJoin(order.user,user)
                .leftJoin(user.company,company)
                .where(searchOptions(keyword, option));
        
        // Spring Data JPA의 PageableExecutionUtils를 사용하여 Page 객체 생성
        return PageableExecutionUtils.getPage(content,pageable,totalCount::fetchOne);
    }
}
