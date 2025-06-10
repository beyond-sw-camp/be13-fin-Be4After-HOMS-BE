package com.beyond.homs.order.repository;

import com.beyond.homs.company.entity.QCompany;
import com.beyond.homs.order.data.OrderSearchOption;
import com.beyond.homs.order.dto.ClaimListResponseDto;
import com.beyond.homs.order.dto.OrderResponseDto;
import com.querydsl.core.types.Expression;
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

import static com.beyond.homs.order.entity.QClaim.claim;
import static com.beyond.homs.order.entity.QOrder.order;
import static com.beyond.homs.company.entity.QCompany.company;
import static com.beyond.homs.user.entity.QUser.user;
import static com.beyond.homs.wms.entity.QDeliveryAddress.deliveryAddress;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    
    // Querydsl 쿼리를 생성하는 핵심 클래스
    // 내부적으로 EntityManager를 사용하여 데이터베이스에 접근
    private final JPAQueryFactory queryFactory;

    // 별칭 충돌을 피하기 위해 새로운 QCompany 인스턴스 생성
    // deliveryAddress를 통한 company 조인에 사용할 별칭
    private final QCompany deliveryCompany = new QCompany("deliveryCompany"); // 새로운 별칭

    // 동적 검색 조건 메서드
    private BooleanExpression searchOptions(String keyword, OrderSearchOption option) {
        if (option == OrderSearchOption.ORDER_CODE){
            return order.orderCode.contains(keyword); // 코드 검색
        } else if (option == OrderSearchOption.COMPANY_NAME){
            return company.companyName.contains(keyword); // 회사 검색
        }
        return null; // 일치하는 옵션 없으면 null
    }

    // 사용자 ID 기반 필터링 조건 추가
    private BooleanExpression userEq(Long userId) {
        if (userId == null) {
            return null; // userId가 null이면 모든 주문 조회 (관리자 케이스)
        }
        // 주문을 생성한 user의 ID와 userId가 일치하는 경우
        return order.user.userId.eq(userId);
    }

    @Override
    public Page<OrderResponseDto> findOrders(OrderSearchOption option, String keyword, Long userId, Pageable pageable) {
        List<OrderResponseDto> content = queryFactory // JPAQueryFactory 사용
                // select문 시작
                .select(Projections.constructor(OrderResponseDto.class, // DTO 인트턴스 직접 생성
                        order.orderId,
                        order.orderCode,
                        company.companyName,
                        deliveryAddress.deliveryName,
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
                .leftJoin(deliveryAddress.company,deliveryCompany)
                .where(
                        searchOptions(keyword, option),  // 동적 검색 조건
                        userEq(userId)                   // 사용자 필터링 조건 추가
                )
                .orderBy(order.orderDate.desc()) // 정렬
                .offset(pageable.getOffset()) // 페이징 시작 오프셋
                .limit(pageable.getPageSize()) // 페이지 크기
                .fetch(); // 실제 쿼리 실행 및 결과 리스트 반환

        // 총 개수 쿼리
        JPAQuery<Long> totalCount = queryFactory
                .select(order.count()) // COUNT 쿼리
                .from(order)
                .leftJoin(order.user,user)
                .leftJoin(user.company,company)
                .leftJoin(deliveryAddress.company,deliveryCompany)
                .where(
                        searchOptions(keyword, option),
                        userEq(userId)
                );
        
        // Spring Data JPA의 PageableExecutionUtils를 사용하여 Page 객체 생성
        return PageableExecutionUtils.getPage(content,pageable,totalCount::fetchOne);
    }

    // 클레임이 있는 모든 주문 검색
    @Override
    public Page<ClaimListResponseDto> findClaimOrders(OrderSearchOption option, String keyword, Long userId, Pageable pageable) {
        List<ClaimListResponseDto> content = queryFactory // JPAQueryFactory 사용
                // select문 시작
                .select(Projections.constructor(ClaimListResponseDto.class, // DTO 인트턴스 직접 생성
                        order.orderId,
                        order.orderCode,
                        company.companyName,
                        order.orderDate,
                        order.dueDate,
                        order.approved,
                        order.parentOrder.orderId,
                        order.rejectReason,
                        order.orderStatus,
                        claim.status
                ))
                .from(claim)
                .leftJoin(claim.orderItem.order,order)
                .leftJoin(order.user,user)
                .leftJoin(user.company,company)
                .where(
                        searchOptions(keyword, option),  // 동적 검색 조건
                        userEq(userId)                   // 사용자 필터링 조건 추가
                )
                .groupBy(order.orderId)
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
                .where(
                        searchOptions(keyword, option),
                        userEq(userId)                   // 사용자 필터링 조건 추가
                )
                .groupBy(order.orderId);

        // Spring Data JPA의 PageableExecutionUtils를 사용하여 Page 객체 생성
        return PageableExecutionUtils.getPage(content,pageable,totalCount::fetchOne);
    }
}
