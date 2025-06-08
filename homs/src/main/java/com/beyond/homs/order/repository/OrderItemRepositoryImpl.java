package com.beyond.homs.order.repository;

import com.beyond.homs.company.entity.QCompany;
import com.beyond.homs.order.dto.OrderItemSearchResponseDto;
import com.beyond.homs.product.data.ProductSearchOption;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.beyond.homs.product.entity.QProductCategory;


import java.util.List;

import static com.beyond.homs.company.entity.QCompany.company;
import static com.beyond.homs.order.entity.QOrder.order;
import static com.beyond.homs.product.entity.QProduct.product;
import static com.beyond.homs.order.entity.QOrderItem.orderItem;
import static com.beyond.homs.user.entity.QUser.user;
import static com.beyond.homs.wms.entity.QDeliveryAddress.deliveryAddress;
import static com.beyond.homs.order.entity.QClaim.claim;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    // Querydsl 쿼리를 생성하는 핵심 클래스
    // 내부적으로 EntityManager를 사용하여 데이터베이스에 접근
    private final JPAQueryFactory queryFactory;

    // 별칭 충돌을 피하기 위해 새로운 QCompany 인스턴스 생성
    // deliveryAddress를 통한 company 조인에 사용할 별칭
    private final QCompany deliveryCompany = new QCompany("deliveryCompany"); // 새로운 별칭

    private final QProductCategory category = new QProductCategory("category");
    private final QProductCategory parentCategory = new QProductCategory("parentCategory");


    // 동적 검색 조건 메서드
    private BooleanExpression searchOptions(String keyword, ProductSearchOption option) {
        if (option == ProductSearchOption.PRODUCT_NAME){
            return product.productName.containsIgnoreCase(keyword); // 상품명
        } else if (option == ProductSearchOption.DOMAIN_NAME){
            return parentCategory.categoryName.containsIgnoreCase(keyword); // 분야
        } else if (option == ProductSearchOption.CATEGORY_NAME){
            return category.categoryName.containsIgnoreCase(keyword); // 분류
        }
        return null; // 일치하는 옵션 없으면 null
    }

    @Override
    public Page<OrderItemSearchResponseDto> findOrderItems(Long targetOrderId, ProductSearchOption option, String keyword, Pageable pageable) {
        // orderId 조건 생성 (null이 아니면 조건 추가)
        BooleanExpression orderIdCondition = (targetOrderId != null) ? order.orderId.eq(targetOrderId) : null;

        List<OrderItemSearchResponseDto> content = queryFactory // JPAQueryFactory 사용
                // select문 시작
                .select(Projections.constructor(OrderItemSearchResponseDto.class, // DTO 인트턴스 직접 생성
                        order.orderId,
                        order.orderCode,
                        company.companyId,
                        company.companyName,
                        order.orderDate,
                        order.dueDate,
                        order.orderStatus,
                        order.approved,
                        claim.status,
                        order.rejectReason,
                        deliveryAddress.deliveryName,
                        product.productId,
                        product.productName,
                        parentCategory.categoryName, // 분야
                        category.categoryName,       // 분류
                        product.productMinQuantity,
                        orderItem.quantity
                ))
                .from(order)
                .leftJoin(order.orderItems,orderItem)
                .leftJoin(orderItem.product, product)
                .leftJoin(claim)
                .on(claim.orderItem.eq(orderItem)) // 조인 조건 명시
                .leftJoin(product.category, category)
                .leftJoin(category.parent, parentCategory)
                .leftJoin(order.user,user)
                .leftJoin(user.company,company)
                .leftJoin(deliveryAddress.company,deliveryCompany)
                .where(
                        orderIdCondition, // orderId 조건
                        searchOptions(keyword, option) // 동적 검색 조건
                )
                .orderBy(product.productId.desc()) // 정렬
                .offset(pageable.getOffset()) // 페이징 시작 오프셋
                .limit(pageable.getPageSize()) // 페이지 크기
                .fetch(); // 실제 쿼리 실행 및 결과 리스트 반환

        // 총 개수 쿼리
        JPAQuery<Long> totalCount = queryFactory
                .select(order.count()) // COUNT 쿼리
                .from(order)
                .leftJoin(order.orderItems,orderItem)
                .leftJoin(orderItem.product,product)
                .leftJoin(order.user,user)
                .leftJoin(user.company,company)
                .leftJoin(deliveryAddress.company,deliveryCompany)
                .where(
                        orderIdCondition,
                        searchOptions(keyword, option)
                );

        // Spring Data JPA의 PageableExecutionUtils를 사용하여 Page 객체 생성
        return PageableExecutionUtils.getPage(content,pageable,totalCount::fetchOne);
    }
}