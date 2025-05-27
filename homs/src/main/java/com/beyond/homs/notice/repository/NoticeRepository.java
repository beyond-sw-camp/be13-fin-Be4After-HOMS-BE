package com.beyond.homs.notice.repository;

import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT new com.beyond.homs.notice.dto.NoticeListDto(n.id, n.title, n.createdAt, n.updatedAt) " +
            "FROM Notice n " +
            "WHERE (:title IS NULL OR n.title LIKE %:title%) ")
    Page<NoticeListDto> searchNotice(
            @Param("title") String title,
            Pageable pageable);
}
