package com.beyond.homs.notice.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.notice.dto.NoticeRequestDto;
import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import com.beyond.homs.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class NoticeControllerImpl implements NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/")
    @Override
    public ResponseEntity<ResponseDto<Page<NoticeListDto>>> noticeList(
            @RequestParam(required = false) String title,
            @PageableDefault(size = 10, page = 0, sort = "id" , direction = Sort.Direction.DESC) Pageable pageable){

        Page<NoticeListDto> noticeSearch = noticeService.getNotices(title,pageable);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "모든 공지사항 목록을 불러왔습니다.",
                        noticeSearch
                ));
    }

    @GetMapping("/{noticeId}")
    @Override
    public ResponseEntity<ResponseDto<NoticeResponseDto>> noticeDetail(
            @PathVariable Long noticeId){

        NoticeResponseDto noticeDetail = noticeService.getNoticeDetail(noticeId);
        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "성공적으로 공지 상세 내용을 불러왔습니다.",
                        noticeDetail
                ));
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<ResponseDto<NoticeResponseDto>> createNotice(
            @RequestBody NoticeRequestDto requestDto){
        NoticeResponseDto notice = noticeService.createNotice(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    new ResponseDto<>(
                            HttpStatus.CREATED.value(),
                            "공지사항이 성공적으로 생성되었습니다.",
                            notice
                ));
    }

    @PutMapping("/update/{noticeId}")
    @Override
    public ResponseEntity<ResponseDto<NoticeResponseDto>> updateNotice(
            @PathVariable Long noticeId,
            @RequestBody NoticeRequestDto requestDto){
        NoticeResponseDto notice = noticeService.updateNotice(noticeId, requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new ResponseDto<>(
                            HttpStatus.OK.value(),
                            "공지사항이 성공적으로 수정되었습니다.",
                            notice
                    ));
    }

    @DeleteMapping("/delete/{noticeId}")
    @Override
    public ResponseEntity<ResponseDto<Void>> deleteNotice(
            @PathVariable Long noticeId){
        noticeService.deleteNotice(noticeId);

        return ResponseEntity.ok(
                new ResponseDto<>(
                        HttpStatus.OK.value(),
                        "공지사항이 성공적으로 삭제되었습니다.",
                        null
                ));
    }
}
