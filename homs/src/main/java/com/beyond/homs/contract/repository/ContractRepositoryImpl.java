package com.beyond.homs.contract.repository;

import com.beyond.homs.contract.data.ContractSearchOption;
import com.beyond.homs.contract.dto.ContractListDto;
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
import static com.beyond.homs.product.entity.QProduct.product;
import static com.beyond.homs.contract.entity.QContract.contract;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepositoryCustom {

    // Querydsl 쿼리를 생성하는 핵심 클래스
    // 내부적으로 EntityManager를 사용하여 데이터베이스에 접근
    private final JPAQueryFactory queryFactory;

    // 동적 검색 조건 메서드
    private BooleanExpression searchOptions(String keyword, ContractSearchOption option) {
        if (option == ContractSearchOption.COMPANY_NAME){
            return company.companyName.contains(keyword); // 회사명
        } else if (option == ContractSearchOption.PRODUCT_NAME){
            return product.productName.contains(keyword); // 상품명
        } else if (option == ContractSearchOption.CATEGORY_NAME){
            return product.category.parent.categoryName.contains(keyword); // 분류
        }
        return null; // 일치하는 옵션 없으면 null
    }

    @Override
    public Page<ContractListDto> findContractList(ContractSearchOption option, String keyword, Pageable pageable) {
        List<ContractListDto> content = queryFactory // JPAQueryFactory 사용
                // select문 시작
                .select(Projections.constructor(ContractListDto.class, // DTO 인트턴스 직접 생성
                        contract.contractId,
                        company.companyName,
                        product.productName,
                        contract.contractStartAt,
                        contract.contractStopAt,
                        product.category.parent.categoryName
                ))
                .from(contract)
                .leftJoin(contract.company,company)
                .leftJoin(contract.product,product)
                .where(
                        searchOptions(keyword, option) // 동적 검색 조건
                )
                .orderBy(contract.contractId.desc()) // 정렬
                .offset(pageable.getOffset()) // 페이징 시작 오프셋
                .limit(pageable.getPageSize()) // 페이지 크기
                .fetch(); // 실제 쿼리 실행 및 결과 리스트 반환

        // 총 개수 쿼리
        JPAQuery<Long> totalCount = queryFactory
                .select(contract.count()) // COUNT 쿼리
                .from(contract)
                .leftJoin(contract.company,company)
                .leftJoin(contract.product,product)
                .where(
                        searchOptions(keyword, option) // 동적 검색 조건
                );

        // Spring Data JPA의 PageableExecutionUtils를 사용하여 Page 객체 생성
        return PageableExecutionUtils.getPage(content,pageable,totalCount::fetchOne);
    }

}
