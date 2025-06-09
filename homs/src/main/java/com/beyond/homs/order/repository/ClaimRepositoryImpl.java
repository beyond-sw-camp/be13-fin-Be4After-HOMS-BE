package com.beyond.homs.order.repository;

import com.beyond.homs.order.data.ClaimSearchOption;
import com.beyond.homs.order.dto.ClaimResponseDto;
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

import static com.beyond.homs.company.entity.QCompany.company;
import static com.beyond.homs.order.entity.QOrder.order;
import static com.beyond.homs.order.entity.QOrderItem.orderItem;
import static com.beyond.homs.product.entity.QProduct.product;
import static com.beyond.homs.user.entity.QUser.user;
import static com.beyond.homs.order.entity.QClaim.claim;

@Repository
@RequiredArgsConstructor
public class ClaimRepositoryImpl implements ClaimRepositoryCustom {

    // Querydsl 쿼리를 생성하는 핵심 클래스
    // 내부적으로 EntityManager를 사용하여 데이터베이스에 접근
    private final JPAQueryFactory queryFactory;

    // 동적 검색 조건 메서드
    private BooleanExpression searchOptions(String keyword, ClaimSearchOption option) {
        if (option == ClaimSearchOption.PRODUCT_NAME){
            return product.productName.contains(keyword); // 상품명
        }
        return null; // 일치하는 옵션 없으면 null
    }

    @Override
    public Page<ClaimResponseDto> findClaim(Long orderId, Long claimId, ClaimSearchOption option, String keyword, Pageable pageable) {
        // orderId 조건 생성 (null이 아니면 조건 추가)
        BooleanExpression orderIdCondition = (orderId != null) ? order.orderId.eq(orderId) : null;
        BooleanExpression claimIdCondition = (claimId != null) ? claim.claimId.eq(claimId) : null;

        List<ClaimResponseDto> content = queryFactory // JPAQueryFactory 사용
                // select문 시작
                .select(Projections.constructor(ClaimResponseDto.class, // DTO 인트턴스 직접 생성
                        claim.claimId,
                        order.orderId,
                        order.orderCode,
                        product.productId,
                        product.productName,
                        company.companyName,
                        claim.reason,
                        claim.details,
                        orderItem.quantity,
                        claim.status
                ))
                .from(claim)
                .leftJoin(claim.orderItem.order,order)
                .leftJoin(claim.orderItem.product,product)
                .leftJoin(order.user,user)
                .leftJoin(user.company,company)
                .where(
                        orderIdCondition,
                        claimIdCondition,
                        searchOptions(keyword, option) // 동적 검색 조건
                )
                .orderBy(product.productId.desc()) // 정렬
                .offset(pageable.getOffset()) // 페이징 시작 오프셋
                .limit(pageable.getPageSize()) // 페이지 크기
                .fetch(); // 실제 쿼리 실행 및 결과 리스트 반환

        // 총 개수 쿼리
        JPAQuery<Long> totalCount = queryFactory
                .select(claim.count()) // COUNT 쿼리
                .from(claim)
                .leftJoin(claim.orderItem.order,order)
                .leftJoin(claim.orderItem.product,product)
                .leftJoin(order.user,user)
                .leftJoin(user.company,company)
                .where(
                        orderIdCondition,
                        claimIdCondition,
                        searchOptions(keyword, option) // 동적 검색 조건
                );

        // Spring Data JPA의 PageableExecutionUtils를 사용하여 Page 객체 생성
        return PageableExecutionUtils.getPage(content,pageable,totalCount::fetchOne);
    }
}